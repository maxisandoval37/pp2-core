package shoppinator.core.facade;

import java.util.Observer;
import service.discovery.ScraperDiscoverer;
import shoppinator.core.ShoppinatorCore;
import shoppinator.core.factory.ShopFactory;
import shoppinator.core.interfaces.Scraper;
import shoppinator.core.interfaces.Shop;
import shoppinator.core.model.Product;
import shoppinator.core.model.criteria.SearchCriteria;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

public class ShoppinatorFacade {

    ShoppinatorCore shoppinatorCore;
    ScraperDiscoverer scraperDiscoverer;
    ShopFactory shopFactory;

    public ShoppinatorFacade() {
        this.scraperDiscoverer = new ScraperDiscoverer();
        this.shopFactory = new ShopFactory();
        this.shoppinatorCore = new ShoppinatorCore();
    }

    public List<Product> searchProductsInShops(SearchCriteria criteria) throws FileNotFoundException {
        shoppinatorCore.setShops(loadShops(criteria));

        return shoppinatorCore.search(criteria);
    }

    public List<Product> getProductList() {
        return shoppinatorCore.getProducts();
    }

    public void subscribe(Object observer) {
        shoppinatorCore.addObserver((Observer) observer);
    }

    private Set<Shop> loadShops(SearchCriteria criteria) throws FileNotFoundException {
        Set<Scraper> scrapers = scraperDiscoverer.discover(criteria.getDiscoverCriteria());

        return shopFactory.create(scrapers);
    }

}
