package service.factory;

import entities.Shop;
import entities.criteria.SearchCriteria;
import entities.criteria.ShopsSelectionCriteria;
import java.io.FileNotFoundException;
import java.util.Set;
import service.discovery.ShopsDiscoverer;
import shoppinator.core.Shoppinator;
import shoppinator.core.ShoppinatorCore;
import shoppinator.core.ShoppinatorCoreImpl;
import utils.PropertiesHelper;

public class ShoppinatorFactory {

    ShopsDiscoverer shopsDiscoverer;

    private final String featuredProduct;

    public ShoppinatorFactory() {
        this.shopsDiscoverer = new ShopsDiscoverer();
        this.featuredProduct = PropertiesHelper.getValue("featured.product");
    }

    public ShoppinatorFactory(String featuredProduct) {
        this.shopsDiscoverer = new ShopsDiscoverer();
        this.featuredProduct = featuredProduct;
    }

    public Shoppinator create(String path) throws FileNotFoundException {
        Set<Shop> shops = shopsDiscoverer.discover(path);
        ShoppinatorCore core = new ShoppinatorCoreImpl(shops);

        // la app inicia con productos destacados
        SearchCriteria initialCriteria = getInitialCriteria(featuredProduct, shops);
        core.search(initialCriteria);

        return new Shoppinator(core, shops);
    }

    private SearchCriteria getInitialCriteria(String featuredProduct, Set<Shop> shops) {
        SearchCriteria initialCriteria = new SearchCriteria();
        initialCriteria.setProductName(featuredProduct);
        initialCriteria.setShopsSelectionCriteria(
            new ShopsSelectionCriteria(shops.stream().map(Shop::getName).toArray(String[]::new))
        );

        return initialCriteria;
    }

}
