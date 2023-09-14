package shoppinator.core;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import service.discovery.ScraperDiscoverer;
import shoppinator.core.factory.ShopFactory;
import shoppinator.core.interfaces.Scraper;
import shoppinator.core.interfaces.Shop;
import java.util.Set;
import shoppinator.core.model.Product;

public class Shoppinator {

    @Getter
    Set<Shop> shops;
    ScraperDiscoverer scraperDiscoverer;
    ShopFactory shopFactory;

    public Shoppinator(String path) {
        this.init(path);
    }

    private void init(String path) {
        this.shopFactory = new ShopFactory();
        this.scraperDiscoverer = new ScraperDiscoverer();

        Set<Scraper> scrapers = scraperDiscoverer.discover(path);
        this.shops = shopFactory.create(scrapers);
    }

    public List<Product> search(String productName) {
        List<Product> products = new ArrayList<>();
        for (Shop shop : this.shops) {
            shop.search(productName);
            products.addAll(shop.getProducts());
        }

        return products;
    }
}
