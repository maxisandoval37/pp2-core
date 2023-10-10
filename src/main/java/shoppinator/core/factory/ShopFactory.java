package shoppinator.core.factory;

import lombok.NoArgsConstructor;
import shoppinator.core.ShopScraper;
import java.util.HashSet;
import java.util.Set;
import shoppinator.core.interfaces.Scraper;
import shoppinator.core.interfaces.Shop;

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
