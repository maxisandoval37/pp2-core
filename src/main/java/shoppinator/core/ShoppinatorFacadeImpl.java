package shoppinator.core;

import java.util.Observer;
import service.discovery.ScraperDiscoverer;
import service.factory.ShopFactory;
import entities.Scraper;
import entities.Shop;
import entities.Product;
import entities.criteria.SearchCriteria;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

public class ShoppinatorFacadeImpl implements ShoppinatorFacade {

    private final ShoppinatorCore shoppinatorCore;
    private final ScraperDiscoverer scraperDiscoverer;
    private final ShopFactory shopFactory;

    public ShoppinatorFacadeImpl() {
        this.scraperDiscoverer = new ScraperDiscoverer();
        this.shopFactory = new ShopFactory();
        this.shoppinatorCore = new ShoppinatorCore();
    }

    @Override
    public List<Product> searchProductsInShops(SearchCriteria criteria) throws FileNotFoundException {
        shoppinatorCore.setShops(loadShops(criteria));

        return shoppinatorCore.search(criteria);
    }

    @Override
    public List<Product> getCurrentProductList() {
        return shoppinatorCore.getProducts();
    }

    @Override
    public Set<Shop> getShops() {
        return shoppinatorCore.getShops();
    }

    @Override
    public void subscribe(Object observer) {
        shoppinatorCore.addObserver((Observer) observer);
    }

    private Set<Shop> loadShops(SearchCriteria criteria) throws FileNotFoundException {
        Set<Scraper> scrapers = scraperDiscoverer.discover(criteria.getDiscoverCriteria());

        return shopFactory.create(scrapers);
    }

}
