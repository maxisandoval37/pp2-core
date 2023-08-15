package service;

import models.Product;
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
import lombok.extern.slf4j.Slf4j;
import static utils.DomainUtils.*;
import static utils.PriceUtils.*;
import static utils.StringUtils.*;

@Slf4j
public class ProductScraper {
    public List<Product> scrapeProducts(String productName) {
        List<Product> allProductList = new ArrayList<>();

        allProductList.addAll(scrapeStoreProductsByName(productName));

        return allProductList;
    }

    private List<Product> scrapeStoreProductsByName(String productName) {
        String storeUrl = "https://www.fravega.com/l/?keyword="+normalizeBlanks(productName,"+")+"&sorting=LOWEST_SALE_PRICE&page=";
        int totalPages = 4;//TODO Obtener de la pagina por medio de un selector css

        List<Callable<List<Product>>> tasks = new ArrayList<>();

        for (int pageNum = 1; pageNum <= totalPages; pageNum++) {//Recorremos cada una de las paginas de la tienda
            final int currentPage = pageNum;
            tasks.add(() -> scrapeProductsFromPage(storeUrl + currentPage, productName));
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
                e.printStackTrace();
            }
        }

        log.info("Total de productos obtenidos de la tienda: " + productList.size());
        //TODO arreglar respuestas (nombres prod) con caracteres invalidos
        return productList;
    }

    private List<Product> scrapeProductsFromPage(String storeUrl, String productName) {
        List<Product> productList = new ArrayList<>();

        try {
            Document documentHtml = Jsoup.connect(storeUrl).get();
            Elements articleElements = documentHtml.select("article.sc-ef269aa1-2.FmCUT");

            for (Element articleElement : articleElements) {
                String name = articleElement.select("span.sc-6321a7c8-0.jKvHol").text();
                Float price = convertPriceStrToFloat(articleElement.select("div.sc-854e1b3a-0.kfAWhD span.sc-ad64037f-0.ixxpWu").text());
                Element link = articleElement.select("a").first();
                String productUrl = "https://www.fravega.com" + link.attr("href");//TODO extraer base url
                String imageUrl = articleElement.select("img[src]").attr("src");

                try{
                    if (normalizeString(name).contains(productName)){
                        Product product = new Product(name, price, extractDomainName(storeUrl),productUrl, imageUrl);
                        productList.add(product);
                    }
                }
                catch (Exception e){
                    log.error("scrapeProducts: Levanto info que no corresponde a un producto valido");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productList;
    }

}