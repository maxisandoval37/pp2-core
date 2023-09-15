package shoppinator.core;

import shoppinator.core.factory.ProductFactory;
import shoppinator.core.interfaces.Scraper;
import shoppinator.core.interfaces.Shop;
import java.util.List;
import shoppinator.core.model.Product;

public class ShopScraper extends Shop {

    private Scraper scraper;
    private ProductFactory productFactory;

    public ShopScraper(Scraper scraper) {
        this.scraper = scraper;
        this.productFactory = new ProductFactory();

        this.searchFeaturedProducts();
    }

    @Override
    public List<Product> search(String productName) {
        String scrapedProduct = scraper.scrap(productName);
        List<Product> products = productFactory.create(scrapedProduct);

        this.addProducts(products);
        return this.getProducts();
    }

    private List<Product> searchFeaturedProducts() {
        String featuredProducts = this.scraper.scrap("featured");
        List<Product> products = productFactory.create(featuredProducts);
        this.addProducts(products);
        return this.getProducts();
    }
}
