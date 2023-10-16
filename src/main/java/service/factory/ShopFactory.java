package service.factory;

import lombok.NoArgsConstructor;
import service.filtering.ProductsFilterer;
import entities.ShopScraper;
import java.util.HashSet;
import java.util.Set;
import entities.Scraper;
import entities.Shop;

@NoArgsConstructor
public class ShopFactory {

    public Set<Shop> create(Set<Scraper> scrapers) {
        Set<Shop> shops = new HashSet<>();
        ProductFactory productFactory = new ProductFactory();
        ProductsFilterer productsFilterer = new ProductsFilterer();

        for (Scraper scraper : scrapers) {
            shops.add(new ShopScraper(scraper, productFactory, productsFilterer));
        }

        return shops;
    }
}
