package service;

import models.Product;
import java.util.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductSearcher {

    FravegaDataExtractor fravegaDataExtractor;
    GarbarinoDataExtractor garbarinoDataExtractor;
    public List<Product> scrapeProducts(String productName) {
        List<Product> allProductList = new ArrayList<>();

        fravegaDataExtractor = new FravegaDataExtractor();
        garbarinoDataExtractor = new GarbarinoDataExtractor();

        allProductList.addAll(Objects.requireNonNull(fravegaDataExtractor.scrapeStoreProductsByName(productName)).getProductList());
        allProductList.addAll(Objects.requireNonNull(garbarinoDataExtractor.scrapeStoreProductsByName(productName)).getProductList());

        allProductList.sort(Comparator.comparing(product -> product.getProductPresentation().getPrice()));

        return allProductList;
    }

}