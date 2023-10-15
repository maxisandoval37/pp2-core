package shoppinator.core.facade;

import java.util.Observer;
import service.discovery.ScraperDiscoverer;
import shoppinator.core.ShoppinatorCore;
import shoppinator.core.factory.SearchCriteriaFactory;
import shoppinator.core.factory.ShopFactory;
import shoppinator.core.interfaces.Scraper;
import shoppinator.core.interfaces.Shop;
import shoppinator.core.model.Product;
import shoppinator.core.model.criteria.DiscoverCriteria;
import shoppinator.core.model.criteria.SearchCriteria;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;
import utils.PropertiesHelper;

public class ShoppinatorFacade {

    ShoppinatorCore shoppinatorCore;
    ScraperDiscoverer scraperDiscoverer;
    ShopFactory shopFactory;
    SearchCriteriaFactory searchCriteriaFactory;
    SearchCriteria criteria;
    SearchCriteria.Memento lastSearchCriteria;
    String featuredProduct;
    Set<Scraper> scrapers;

    public ShoppinatorFacade() {
        this.scraperDiscoverer = new ScraperDiscoverer();
        this.searchCriteriaFactory = new SearchCriteriaFactory();
        this.shopFactory = new ShopFactory();
        this.shoppinatorCore = new ShoppinatorCore();

        this.featuredProduct = PropertiesHelper.getValue("featured.product");
    }

    public void init(String path) throws FileNotFoundException {
        this.criteria = searchCriteriaFactory.create(new String[]{path, featuredProduct});
        performSearch();
    }

    public List<Product> search(String... params) throws FileNotFoundException {
        if (params.length == 0) {
            criteria.restoreCriteria(lastSearchCriteria);
        } else {
            this.criteria = searchCriteriaFactory.create(params);
        }

        return performSearch();
    }

    public List<Product> getProductList() {
        return shoppinatorCore.getProducts();
    }

    public Set<Scraper> getScrapers() { return scrapers; }

    private List<Product> performSearch() throws FileNotFoundException {
        this.lastSearchCriteria = criteria.saveState();

        shoppinatorCore.setShops(loadShops(criteria.getDiscoverCriteria()));
        return shoppinatorCore.search(criteria);
    }

    private Set<Shop> loadShops(DiscoverCriteria criteria) throws FileNotFoundException {
        scrapers = scraperDiscoverer.discover(criteria);

        return shopFactory.create(scrapers);
    }

    public void subscribe(Object observer) {
        this.shoppinatorCore.addObserver((Observer) observer);
    }
}
