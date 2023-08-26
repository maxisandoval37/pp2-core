package service;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import models.Product;
import models.ProductImage;
import models.ProductPresentation;
import models.ShopProduct;
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
import static utils.PriceUtils.*;
import static utils.StringUtils.*;

@Slf4j
@AllArgsConstructor
public class ProductSearcher {

    private final String[] shopsUrls;

    public List<Product> searchProducts(String productName) {

        return searchProductsByName(productName);
    }

    private List<Product> searchProductsByName(String productName) {
        for (String shopUrl : shopsUrls) {
            List<Product> productList = searchProductsByNameInShop(productName, shopUrl);

            if (!productList.isEmpty()) {
                return productList;
            }
        }

        return Collections.emptyList();
    }

    private List<Product> searchProductsByNameInShop(String productName, String shopUrl) {
        int totalPages = 4;//TODO Obtener de la pagina por medio de un selector css

        List<Callable<List<Product>>> tasks = new ArrayList<>();

        //Recorremos cada una de las paginas de la tienda
        for (int pageNum = 1; pageNum <= totalPages; pageNum++) {
            final int currentPage = pageNum;
            tasks.add(() -> searchProductsFromWebpage(String.format(shopUrl, currentPage), productName));
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
                log.error("searchProductsByNameInShop: Error al obtener productos de la tienda {}", e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        log.info("Total de productos obtenidos de la tienda: {}", productList.size());
        //TODO arreglar respuestas (nombres prod) con caracteres invalidos
        return productList;
    }

    private List<Product> searchProductsFromWebpage(String shopUrl, String productName) {
        List<Product> productList = new ArrayList<>();

        try {
            Document documentHtml = Jsoup.connect(shopUrl).get();
            Elements articleElements = documentHtml.select("article.sc-ef269aa1-2.FmCUT");

            for (Element articleElement : articleElements) {
                Product product = mapElementToProduct(articleElement, shopUrl, productName);

                if (product != null) {
                    productList.add(product);
                }
            }
        } catch (IOException e) {
            log.warn("searchProductsFromWebpage: Error al obtener productos de la tienda {}", e.getMessage());
        }

        return productList;
    }

    private Product mapElementToProduct(Element articleElement, String shopUrl, String productName) {
        String name = articleElement.select("span.sc-6321a7c8-0.jKvHol").text();
        Float price = convertPriceStrToFloat(articleElement.select("div.sc-854e1b3a-0.kfAWhD span.sc-ad64037f-0.ixxpWu").text());
        Element link = articleElement.select("a").first();
        // TODO replace by something like String productUrl = String.format(shopUrl, productName);
        String productUrl = "https://www.fravega.com" + link.attr("href");//TODO extraer base url
        String imageUrl = articleElement.select("img[src]").attr("src");

        try {
            if (normalizeString(name).contains(productName)){
                ProductImage productImage = new ProductImage(imageUrl);
                ShopProduct shopProduct = new ShopProduct(productUrl);
                ProductPresentation productPresentation = new ProductPresentation(BigDecimal.valueOf(price), shopProduct, productImage);

                return new Product(name, productPresentation);
            }
        }
        catch (Exception e){
            log.error("searchProducts: Levanto info que no corresponde a un producto valido");
        }

        return null;
    }
}