package entities;

import java.util.Collections;
import service.factory.ProductFactory;
import entities.Scraper;
import entities.Shop;
import java.util.List;
import entities.Product;

public class ShopScraper extends Shop {

    private Scraper scraper;
    private ProductFactory productFactory;

    public ShopScraper(Scraper scraper, ProductFactory productFactory) {
        this.scraper = scraper;
        this.productFactory = productFactory;
    }

    @Override
    public List<Product> search(String productName) {
        String scrapedProduct = scraper.scrap(productName);
        List<Product> products = productFactory.create(scrapedProduct);

        if(!products.isEmpty()) {
            this.addProducts(products);
        } else {
            this.addProducts(Collections.emptyList());
        }

        return this.getProducts();
    }
}
