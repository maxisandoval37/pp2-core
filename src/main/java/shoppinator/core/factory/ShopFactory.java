package shoppinator.core.factory;

import shoppinator.core.ShopProxy;
import shoppinator.core.ShopScraper;
import java.util.HashSet;
import java.util.Set;
import shoppinator.core.interfaces.Scrapper;
import shoppinator.core.interfaces.Shop;

public class ShopFactory {

    public ShopFactory() {
    }

    public Set<Shop> create(Set<Scrapper> scrappers) {
        Set<Shop> shops = new HashSet<>();

        for (Scrapper scrapper : scrappers) {
            ShopScraper shopScraper = new ShopScraper(scrapper);
            shops.add(new ShopProxy(shopScraper));
        }

        return shops;
    }
}
