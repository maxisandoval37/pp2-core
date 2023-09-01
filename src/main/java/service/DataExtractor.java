package service;

import models.Shop;

public interface DataExtractor {

    Shop scrapeStoreProductsByName(String productName);
}
