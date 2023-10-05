package shoppinator.core.facade;

import service.discovery.ScraperDiscoverer;
import shoppinator.core.factory.SearchCriteriaFactory;
import shoppinator.core.factory.ShopFactory;
import shoppinator.core.interfaces.Scraper;
import shoppinator.core.interfaces.Shop;
import shoppinator.core.model.Product;
import shoppinator.core.model.SearchCriteria;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ShoppinatorFacade {

    public Set<Shop> loadShops(String... params) throws FileNotFoundException {
        ShopFactory shopFactory = new ShopFactory();
        ScraperDiscoverer scraperDiscoverer = new ScraperDiscoverer();
        Set<Scraper> scrapers = scraperDiscoverer.discoverSelectedShops(params[0], params);

        return shopFactory.create(scrapers);
    }

    public SearchCriteria createCriteria(String... params) {
        SearchCriteriaFactory searchCriteriaFactory = new SearchCriteriaFactory();

        return searchCriteriaFactory.create(params);
    }

    public void search(Set<Shop> shops, SearchCriteria searchCriteria) {
        for (Shop shop : shops) {
            shop.search(searchCriteria);
        }
    }
}
