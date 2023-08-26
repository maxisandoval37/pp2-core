package service;

import models.Product;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductSearcher {

    private final String[] shopsUrls;
    WebpageSearcher webpageSearcher;

    public ProductSearcher(String[] shopsUrls) {
        this.shopsUrls = shopsUrls;
        this.webpageSearcher = new WebpageSearcher();
    }

    public ProductSearcher() {
        this.shopsUrls = null;
        this.webpageSearcher = null;
    }

    public List<Product> searchProducts(String productName) {
        return searchProductsByName(productName);
    }

    private List<Product> searchProductsByName(String productName) {
        if (shopsUrls != null) {
            for (String shopUrl : shopsUrls) {
                List<Product> productList = searchProductsByNameInShop(productName, shopUrl);

                if (!productList.isEmpty()) {
                    return productList;
                }
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
            tasks.add(
                () -> this.webpageSearcher.searchProductsFromWebpage(String.format(shopUrl, currentPage), productName));
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
}