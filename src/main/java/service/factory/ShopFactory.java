package service.factory;

import lombok.NoArgsConstructor;
import entities.ShopScraper;
import java.util.HashSet;
import java.util.Set;
import entities.Scraper;
import entities.Shop;

@NoArgsConstructor
public class ShopFactory {

    public Set<Shop> create(Set<Scraper> scrapers) {
        Set<Shop> shops = new HashSet<>();

        for (Scraper scraper : scrapers) {
            ProductFactory productFactory = new ProductFactory();

            shops.add(new ShopScraper(scraper, productFactory));
        }

        return shops;
    }
}
