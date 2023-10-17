package entities;

import lombok.Getter;
import service.filtering.ProductsFilterer;
import service.factory.ProductFactory;
import java.util.List;
import entities.criteria.SearchCriteria;

public class ShopScraper extends Shop {

    @Getter
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
