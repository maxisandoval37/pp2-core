package shoppinator.core.facade;

import service.discovery.ScraperDiscoverer;
import shoppinator.core.ShoppinatorCore;
import shoppinator.core.factory.SearchCriteriaFactory;
import shoppinator.core.factory.ShopFactory;
import shoppinator.core.interfaces.Scraper;
import shoppinator.core.interfaces.Shop;
import shoppinator.core.model.Product;
import shoppinator.core.model.SearchCriteria;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

public class ShoppinatorFacade {

    ShoppinatorCore shoppinatorCore;
    ScraperDiscoverer scraperDiscoverer;
    ShopFactory shopFactory;
    SearchCriteriaFactory searchCriteriaFactory;

    // esto inclusive podriamos tenerlo en una propertie
    String featuredProduct = "microondas";

    public ShoppinatorFacade() {
        this.scraperDiscoverer = new ScraperDiscoverer();
        this.searchCriteriaFactory = new SearchCriteriaFactory();
        this.shopFactory = new ShopFactory();
        shoppinatorCore = new ShoppinatorCore();
    }

    public void init(String path) throws FileNotFoundException {
        shoppinatorCore.setShops(loadShops(path));

        SearchCriteria criteria = searchCriteriaFactory.create(new String[]{featuredProduct});
        shoppinatorCore.search(criteria);
    }

    // ["/plugins", "batman", 100, 10, "fravega", "garbarino"]
    public List<Product> search(String... params) throws FileNotFoundException {
        shoppinatorCore.setShops(loadSelectedShops(params));

        SearchCriteria criteria = searchCriteriaFactory.create(params);
        return shoppinatorCore.search(criteria);
    }

    public List<Product> getProductList() {
        return shoppinatorCore.getProducts();
    }

    private Set<Shop> loadSelectedShops(String... params) throws FileNotFoundException {
        // podriamos dejar esto más extensible (crear otro objeto que sea como un
        // searchcriteria pero para hacer el discover de los scrapers seleccionados)
        Set<Scraper> scrapers = scraperDiscoverer.discoverSelectedShops(
                params[0],
                Arrays.copyOfRange(params, 4, params.length)
        );

        return shopFactory.create(scrapers);
    }

    private Set<Shop> loadShops(String path) throws FileNotFoundException {
        Set<Scraper> scrapers = scraperDiscoverer.discover(path);

        return shopFactory.create(scrapers);
    }
}
