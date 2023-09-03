package extensions;

import interfaces.Shop;
import java.net.ConnectException;
import lombok.Getter;
import lombok.SneakyThrows;
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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import utils.DomainUtils;

import static utils.PriceUtils.convertPriceStrToFloat;
import static utils.StringUtils.normalizeString;

@Slf4j
@Getter
public class FravegaShop extends Shop {

    public FravegaShop() {
        super("Fravega", "https://www.fravega.com/l/?keyword=%s&sorting=LOWEST_SALE_PRICE&page=");
    }

    @Override
    public List<Product> products(String productName) {
        String formattedProductName = productName.replace(" ","+");
        String productSearchUrl = String.format(this.getUrl(), formattedProductName);

        List<Callable<List<Product>>> tasks = new ArrayList<>();
        int pageNum = 1;
        boolean productsExists = true;
        //Recorremos cada una de las paginas de la tienda
        while(productsExists) {
            String pageSearchUrl = productSearchUrl + pageNum;
            List<Product> products = this.scrapeProductsFromPage(pageSearchUrl, productName);
            pageNum++;
            //Si llego al final de las paginas, salimos
            productsExists &= !products.isEmpty();
            tasks.add(() -> products);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<List<Product>>> futures;

        try {
            futures = executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return Collections.emptyList();
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

    @SneakyThrows
    private List<Product> scrapeProductsFromPage(String pageSearchUrl, String productName) {
        List<Product> productList = new ArrayList<>();

        try {
            Connection connection = Jsoup.connect(pageSearchUrl);
            connection.header("Content-Type", "text/html; charset=UTF-8");
            Document documentHtml = connection.get();
            Elements articleElements = documentHtml.select("article.sc-ef269aa1-2.FmCUT");

            for (Element articleElement : articleElements) {
                String shopProductName = articleElement.select("span.sc-6321a7c8-0.jKvHol").text();
                Float price = convertPriceStrToFloat(articleElement.select("div.sc-854e1b3a-0.kfAWhD span.sc-ad64037f-0.ixxpWu").text());
                Element link = articleElement.select("a").first();
                String productUrl = link != null ? "https://www.fravega.com" + link.attr("href") : "";
                String imageUrl = articleElement.select("img[src]").attr("src");

                if (normalizeString(shopProductName).contains(normalizeString(productName))) {
                    ProductPresentation productPresentation = new ProductPresentation(price, imageUrl);
                    Product product = new Product(shopProductName, productUrl, productPresentation);
                    productList.add(product);
                }
            }
        } catch (Exception e) {
            throw new ConnectException(e.getMessage());
        }

        return productList;
    }
}
