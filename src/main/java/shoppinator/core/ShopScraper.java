package shoppinator.core;

import shoppinator.core.factory.ProductFactory;
import shoppinator.core.interfaces.Scraper;
import shoppinator.core.interfaces.Shop;
import java.util.List;
import shoppinator.core.model.Product;
import shoppinator.core.model.SearchCriteria;

public class ShopScraper extends Shop {

    private final Scraper scraper;
    private final ProductFactory productFactory;

    public ShopScraper(Scraper scraper) {
        this.scraper = scraper;
        this.productFactory = new ProductFactory();
    }

    @Override
    public List<Product> search(SearchCriteria criteria) {
        String scrapedProduct = scraper.scrap(criteria.getProductName());
        List<Product> products = productFactory.create(scrapedProduct);

        // TODO aca iria el filter

        this.addProducts(products);
        return this.getProducts();
    }

}
