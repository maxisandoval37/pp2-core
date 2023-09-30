package shoppinator.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import lombok.Getter;
import service.discovery.ScraperDiscoverer;
import shoppinator.core.factory.ShopFactory;
import shoppinator.core.interfaces.Scraper;
import shoppinator.core.interfaces.Shop;
import shoppinator.core.model.Product;

@SuppressWarnings("deprecation")
public class Shoppinator extends Observable implements Observer {

    @Getter
    List<Product> products;
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
        this.addObservers();
    }

    public List<Product> search(String productName) {
        this.products = new ArrayList<>();
        for (Shop shop : this.shops) {
            shop.search(productName);
        }

        return products;
    }

    @Override
    public void update(Observable o, Object productList) {
        this.products.addAll((List<Product>) productList);
        products.sort(Comparator.comparing(p -> p.getProductPresentation().getPrice()));
    }

    private void addObservers() {
        for (Shop shop : this.shops) {
            shop.addObserver(this);
        }
    }

}
