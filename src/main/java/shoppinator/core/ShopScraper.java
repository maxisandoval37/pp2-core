package shoppinator.core;

import shoppinator.core.factory.ProductFactory;
import shoppinator.core.interfaces.Scrapper;
import shoppinator.core.interfaces.Shop;
import java.util.List;
import shoppinator.core.model.Product;

public class ShopScrapper extends Shop {

    private Scrapper scrapper;
    private ProductFactory productFactory;

    public ShopScrapper(Scrapper scrapper) {
        this.searchFeaturedProducts();

        this.scrapper = scrapper;
    }

    @Override
    public List<Product> search(String productName) {
        String scrapedProduct = scrapper.scrap(productName);
        List<Product> products = productFactory.create(scrapedProduct);

        this.addProducts(products);
        return this.getProducts();
    }

    private List<Product> searchFeaturedProducts() {
        String featuredProducts = this.scrapper.scrapFeatured();
        return productFactory.create(featuredProducts);
    }
}
