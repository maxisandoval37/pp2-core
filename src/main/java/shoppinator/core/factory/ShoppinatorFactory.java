package shoppinator.core.factory;

import shoppinator.core.Shoppinator;
import shoppinator.core.interfaces.Shop;
import shoppinator.core.interfaces.Scrapper;
import service.discovery.ScrapperDiscoverer;
import java.util.Set;

public class ShoppinatorFactory {

    ScrapperDiscoverer scrapperDiscoverer;
    ShopFactory shopFactory;

    public ShoppinatorFactory(ScrapperDiscoverer scrapperDiscoverer, ShopFactory shopFactory) {

        this.scrapperDiscoverer = scrapperDiscoverer;
        this.shopFactory = shopFactory;
    }

    public Shoppinator create(String path) {
        Set<Scrapper> scrappers = scrapperDiscoverer.discover(path);
        Set<Shop> shops = shopFactory.create(scrappers);

        return new Shoppinator(shops);
    }
}
