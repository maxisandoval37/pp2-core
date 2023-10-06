package shoppinator.core.facade;

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

    String featuredProduct;

    public ShoppinatorFacade() {
        this.scraperDiscoverer = new ScraperDiscoverer();
        this.searchCriteriaFactory = new SearchCriteriaFactory();
        this.shopFactory = new ShopFactory();
        this.shoppinatorCore = new ShoppinatorCore();

        this.featuredProduct = PropertiesHelper.getValue("featured.product");
    }

    public void init(String path) throws FileNotFoundException {
        SearchCriteria criteria = searchCriteriaFactory.create(new String[]{path, featuredProduct});
        performSearch(criteria);
    }

    public List<Product> search(String... params) throws FileNotFoundException {
        SearchCriteria criteria = searchCriteriaFactory.create(params);
        return performSearch(criteria);
    }

    public List<Product> getProductList() {
        return shoppinatorCore.getProducts();
    }

    private List<Product> performSearch(SearchCriteria criteria) throws FileNotFoundException {
        shoppinatorCore.setShops(loadShops(criteria.getDiscoverCriteria()));
        return shoppinatorCore.search(criteria);
    }

    private Set<Shop> loadShops(DiscoverCriteria criteria) throws FileNotFoundException {
        Set<Scraper> scrapers = scraperDiscoverer.discover(criteria);

        return shopFactory.create(scrapers);
    }
}
