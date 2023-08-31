package service;

import models.Product;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductSearcher {

    FravegaDataExtractor fravegaDataExtractor;
    GarbarinoDataExtractor garbarinoDataExtractor;
    public List<Product> scrapeProducts(String productName) throws InterruptedException, ExecutionException {
        List<Product> allProductList = new ArrayList<>();

        fravegaDataExtractor = new FravegaDataExtractor();
        garbarinoDataExtractor = new GarbarinoDataExtractor();

        ExecutorService executorService = Executors.newFixedThreadPool(2); // cant. hilos (1 por tienda (shop))

        Future<List<Product>> fravegaProductListF = executorService.submit(() -> fravegaDataExtractor.scrapeStoreProductsByName(productName).getProductList());
        Future<List<Product>> garbarinoProductListF = executorService.submit(() -> garbarinoDataExtractor.scrapeStoreProductsByName(productName).getProductList());

        allProductList.addAll(fravegaProductListF.get());
        allProductList.addAll(garbarinoProductListF.get());

        executorService.shutdown();

        allProductList.sort(Comparator.comparing(product -> product.getProductPresentation().getPrice()));

        return allProductList;
    }

}