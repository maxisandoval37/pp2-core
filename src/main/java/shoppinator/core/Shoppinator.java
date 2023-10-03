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
import shoppinator.core.factory.SearchCriteriaFactory;
import shoppinator.core.factory.ShopFactory;
import shoppinator.core.interfaces.Scraper;
import shoppinator.core.interfaces.Shop;
import shoppinator.core.model.Product;
import shoppinator.core.model.SearchCriteria;

@SuppressWarnings("deprecation")
public class Shoppinator extends Observable implements Observer {

    @Getter
    List<Product> products;
    @Getter
    Set<Shop> shops;
    ScraperDiscoverer scraperDiscoverer;
    ShopFactory shopFactory;
    SearchCriteriaFactory searchCriteriaFactory;

    public Shoppinator(String path) throws FileNotFoundException {
        this.init(path);
    }

    private void init(String path) throws FileNotFoundException {
        this.shopFactory = new ShopFactory();
        this.scraperDiscoverer = new ScraperDiscoverer();
        this.searchCriteriaFactory = new SearchCriteriaFactory();

        Set<Scraper> scrapers = scraperDiscoverer.discover(path);
        this.shops = shopFactory.create(scrapers);
        this.addObservers();

        // busqueda inicial de productos destacados
        // , en el scraper que esta por defecto
        this.search("featured");
    }

    public List<Product> search(String... params) {
        SearchCriteria searchCriteria = searchCriteriaFactory.create(params);

        this.products = new ArrayList<>();

        for (Shop shop : this.shops) {
            shop.search(searchCriteria);
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
