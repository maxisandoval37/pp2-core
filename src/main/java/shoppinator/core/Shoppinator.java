package shoppinator.core;

import java.io.FileNotFoundException;
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
    @Getter
    Set<Shop> shops;
    @Getter
    List<Product> products;
    ScraperDiscoverer scraperDiscoverer;
    ShopFactory shopFactory;

    public Shoppinator(String path) throws FileNotFoundException {
        this.init(path);

        // la app inicia con productos destacados
        this.search("mouse");
    }

    private void init(String path) throws FileNotFoundException {
        this.shopFactory = new ShopFactory();
        this.scraperDiscoverer = new ScraperDiscoverer();

        Set<Scraper> scrapers = scraperDiscoverer.discover(path);
        Set<Shop> shopSet = shopFactory.create(scrapers);
        this.setShops(shopSet);
    }

    public List<Product> search(String productName) {
        products = new ArrayList<>();
      
        for (Shop shop : this.shops) {
            shop.search(productName);
        }

        return products;
    }

    @Override
    public void update(Observable o, Object productList) {
        this.products.addAll((List<Product>) productList);
        products.sort(Comparator.comparing(p -> p.getProductPresentation().getPrice()));

        sendNotification();
    }

    public boolean sendNotification() {
        setChanged();
        super.notifyObservers(this.products);
        return hasChanged();
    }

    public void setShops(Set<Shop> shops) {
        this.shops = shops;
        this.addObservers();
    }

    private void addObservers() {
        for (Shop shop : this.shops) {
            shop.addObserver(this);
        }
    }
}
