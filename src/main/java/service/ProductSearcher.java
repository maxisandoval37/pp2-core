package service;

import models.Product;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("deprecation")
@Slf4j
public class ProductSearcher extends Observable {

    private final Set<DataExtractor> dataExtractors;

    public ProductSearcher() {
        this.dataExtractors = new HashSet<>();
        dataExtractors.add(new FravegaDataExtractor());
        dataExtractors.add(new GarbarinoDataExtractor());
    }

    public List<Product> scrapeProducts(String productName) throws InterruptedException, ExecutionException {
        List<Product> allProductList = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(dataExtractors.size());

        for (DataExtractor dataExtractor : dataExtractors) {
            Future<List<Product>> productListF = executorService.submit(
                () -> dataExtractor.scrapeStoreProductsByName(productName).getProductList());
            allProductList.addAll(productListF.get());
        }

        executorService.shutdown();

        allProductList.sort(Comparator.comparing(product -> product.getProductPresentation().getPrice()));

        this.sendProductList(allProductList);
        return allProductList;
    }

    public void sendProductList(List<Product> productList) {
        setChanged();
        notifyObservers(productList);
    }
}