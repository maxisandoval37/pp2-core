package extensions;

import interfaces.Shop;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import models.Product;
import models.ProductPresentation;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static utils.PriceUtils.convertPriceStrToFloat;
import static utils.StringUtils.normalizeString;

@Slf4j
@Getter
public class GarbarinoShop extends Shop {

    private final String urlDomain = "https://www.garbarino.com";

    public GarbarinoShop() {
        super("Garbarino", "https://www.garbarino.com/shop/sort-by-price-low-to-high?search=%s");
    }

    @Override
    public List<Product> products(String productName) {
        String formattedProductName = productName.replace(" ","%20");

        List<Callable<List<Product>>> tasks = new ArrayList<>();

        List<Product> products = scrapeProductsFromPage(productName);
        tasks.add(() -> products);

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<List<Product>>> futures;

        try {
            futures = executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            executorService.shutdown();
        }

        List<Product> productList = new ArrayList<>();
        for (Future<List<Product>> future : futures) {
            try {
                productList.addAll(future.get());
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                log.warn(e.getMessage());
            }
        }

        log.info("Total de productos obtenidos de la tienda ({}): {}", this.getName(), productList.size());

        return productList;
    }

    private List<Product> scrapeProductsFromPage(String productName) {
        Set<Product> productSet = new HashSet<>();

        try {
            Connection connection = Jsoup.connect(String.format(this.getUrl(), productName));
            connection.header("Content-Type", "text/html; charset=UTF-8");
            Document documentHtml = connection.get();
            Elements articleElements = documentHtml.select("div[data-v-584255dc]");

            for (Element articleElement : articleElements) {
                String shopProductName = articleElement.select("div[data-v-584255dc] a.card-anchor.header div[data-v-584255dc] div.font-1").text();

                if (!shopProductName.isEmpty()){
                    Float price = convertPriceStrToFloat(articleElement.select("div[data-v-584255dc] div.product-card-center-aligned-vertical__price span[data-v-9251c388]:last-child").text());
                    Element linkImg = articleElement.select("a").first();
                    String productUrl = (linkImg != null) ? (this.urlDomain + linkImg.attr("href")) : "";
                    String imageUrl = articleElement.select("img[src]").attr("src");

                    if (normalizeString(shopProductName).contains(normalizeString(productName))) {
                        ProductPresentation productPresentation = new ProductPresentation(price, imageUrl);
                        Product product = new Product(shopProductName, productUrl, productPresentation);
                        productSet.add(product);
                    }
                }

            }
        } catch (IOException e) {
            log.warn(e.getMessage());
        }

        return new ArrayList<>(productSet);
    }
}
