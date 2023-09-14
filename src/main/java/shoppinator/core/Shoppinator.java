package shoppinator.core;

import java.util.ArrayList;
import java.util.List;
import service.discovery.ScrapperDiscoverer;
import shoppinator.core.factory.ShopFactory;
import shoppinator.core.interfaces.Scrapper;
import shoppinator.core.interfaces.Shop;
import java.util.Set;
import shoppinator.core.model.Product;

public class Shoppinator {

    Set<Shop> shops;
    ScrapperDiscoverer scrapperDiscoverer;
    ShopFactory shopFactory;

    public Shoppinator(String path) {
        this.init(path);
    }

    private void init(String path) {
        this.shopFactory = new ShopFactory();
        this.scrapperDiscoverer = new ScrapperDiscoverer();

        Set<Scrapper> scrappers = scrapperDiscoverer.discover("src/dist/lib/plugins/");
        this.shops = shopFactory.create(scrappers);
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
