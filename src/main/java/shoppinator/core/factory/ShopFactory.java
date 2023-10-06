package shoppinator.core.factory;

import lombok.NoArgsConstructor;
import service.ProductsFilterer;
import shoppinator.core.ShopScraper;
import java.util.HashSet;
import java.util.Set;
import shoppinator.core.interfaces.Scraper;
import shoppinator.core.interfaces.Shop;

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
