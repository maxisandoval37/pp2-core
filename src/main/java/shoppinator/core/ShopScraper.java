package shoppinator.core;

import service.ProductsFilterer;
import shoppinator.core.factory.ProductFactory;
import shoppinator.core.interfaces.Scraper;
import shoppinator.core.interfaces.Shop;
import java.util.List;
import shoppinator.core.model.Product;
import shoppinator.core.model.criteria.SearchCriteria;

public class ShopScraper extends Shop {

    private final Scraper scraper;
    private final ProductFactory productFactory;
    private final ProductsFilterer productsFilterer;

    public ShopScraper(Scraper scraper, ProductFactory productFactory, ProductsFilterer productsFilterer) {
        this.scraper = scraper;
        this.productFactory = productFactory;
        this.productsFilterer = productsFilterer;
    }

    @Override
    public List<Product> search(SearchCriteria criteria) {
        String scrapResult = scraper.scrap(criteria.getProductName());
        List<Product> products = productFactory.create(scrapResult);
        List<Product> filtered = productsFilterer.filter(criteria.getFilterCriteria(), products);

        this.addProducts(filtered);
        return this.getProducts();
    }

}
