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
        // TODO estaría mal pasarle el mismo productFactory y productsFilterer
        //      a todos los shops?
        for (Scraper scraper : scrapers) {
            ProductFactory productFactory = new ProductFactory();
            ProductsFilterer productsFilterer = new ProductsFilterer();

            shops.add(new ShopScraper(scraper, productFactory, productsFilterer));
        }

        return shops;
    }
}
