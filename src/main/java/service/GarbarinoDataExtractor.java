package service;

import lombok.extern.slf4j.Slf4j;
import models.Product;
import models.ProductPresentation;
import models.Shop;
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
import static utils.DomainUtils.extractDomainName;
import static utils.PriceUtils.convertPriceStrToFloat;
import static utils.StringUtils.normalizeBlanks;
import static utils.StringUtils.normalizeString;

@Slf4j
public class GarbarinoDataExtractor {

    public Shop scrapeStoreProductsByName(String productName) {
        Shop shop = new Shop();
        shop.setShopUrlSearch("https://www.garbarino.com/shop/sort-by-price-low-to-high?search="+normalizeBlanks(productName,"%20"));
        shop.setShopUrlDomain("https://www.garbarino.com");
        shop.setStoreName(extractDomainName(shop.getShopUrlDomain()));

        List<Callable<List<Product>>> tasks = new ArrayList<>();

        List<Product> products = scrapeProductsFromPage(shop, productName);
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
                e.printStackTrace();
            }
        }

        log.info("Total de productos obtenidos de la tienda ("+shop.getStoreName()+"): " + productList.size());

        shop.setProductList(productList);
        return shop;
    }

    private List<Product> scrapeProductsFromPage(Shop shop, String productName) {
        Set<Product> productSet = new HashSet<>();

        try {
            Connection connection = Jsoup.connect(shop.getShopUrlSearch());
            connection.header("Content-Type", "text/html; charset=UTF-8");
            Document documentHtml = connection.get();
            Elements articleElements = documentHtml.select("div[data-v-584255dc]");

            for (Element articleElement : articleElements) {
                String name = articleElement.select("div[data-v-584255dc] a.card-anchor.header div[data-v-584255dc] div.font-1").text();

                if (!name.equals("")){
                    Float price = convertPriceStrToFloat(articleElement.select("div[data-v-584255dc] div.product-card-center-aligned-vertical__price span[data-v-9251c388]:last-child").text());
                    Element linkImg = articleElement.select("a").first();
                    String productUrl = (linkImg != null) ? (shop.getShopUrlDomain() + linkImg.attr("href")) : "";
                    String imageUrl = articleElement.select("img[src]").attr("src");

                    if (normalizeString(name).contains(productName)){
                        ProductPresentation productPresentation = new ProductPresentation(price, imageUrl);
                        Product product = new Product(name, productUrl, productPresentation);
                        productSet.add(product);
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(productSet);
    }
}