package shoppinator.core.factory;

import shoppinator.core.ShopProxy;
import shoppinator.core.ShopScrapper;
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
            ShopScrapper shopScrapper = new ShopScrapper(scrapper);
            shops.add(new ShopProxy(shopScrapper));
        }

        return shops;
    }
}
