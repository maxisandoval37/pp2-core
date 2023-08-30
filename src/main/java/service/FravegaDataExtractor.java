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
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import static utils.DomainUtils.extractDomainName;
import static utils.PriceUtils.convertPriceStrToFloat;
import static utils.StringUtils.normalizeBlanks;
import static utils.StringUtils.normalizeString;

@Slf4j
public class FravegaDataExtractor {

    public Shop scrapeStoreProductsByName(String productName) {
        Shop shop = new Shop();
        String shopUrlSearch = "https://www.fravega.com/l/?keyword="+normalizeBlanks(productName,"+")+"&sorting=LOWEST_SALE_PRICE&page=";
        shop.setStoreName(extractDomainName(shopUrlSearch));
        shop.setShopUrlDomain("https://www.fravega.com");

        List<Callable<List<Product>>> tasks = new ArrayList<>();

        for (int pageNum = 1; pageNum <= 99; pageNum++) {//Recorremos cada una de las paginas de la tienda
            shop.setShopUrlSearch(shopUrlSearch + pageNum);
            List<Product> products = scrapeProductsFromPage(shop, productName);
            if (products.isEmpty()) {
                break;//Si llego al final de las paginas, salimos
            }

            tasks.add(() -> products);
        }

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
        List<Product> productList = new ArrayList<>();

        try {
            Connection connection = Jsoup.connect(shop.getShopUrlSearch());
            connection.header("Content-Type", "text/html; charset=UTF-8");
            Document documentHtml = connection.get();
            Elements articleElements = documentHtml.select("article.sc-ef269aa1-2.FmCUT");

            for (Element articleElement : articleElements) {
                String name = articleElement.select("span.sc-6321a7c8-0.jKvHol").text();
                Float price = convertPriceStrToFloat(articleElement.select("div.sc-854e1b3a-0.kfAWhD span.sc-ad64037f-0.ixxpWu").text());
                Element link = articleElement.select("a").first();
                String productUrl = link != null ? shop.getShopUrlDomain() + link.attr("href") : "";
                String imageUrl = articleElement.select("img[src]").attr("src");

                if (normalizeString(name).contains(normalizeString(productName))) {
                    ProductPresentation productPresentation = new ProductPresentation(price, imageUrl);
                    Product product = new Product(name, productUrl, productPresentation);
                    productList.add(product);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productList;
    }
}