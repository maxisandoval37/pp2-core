package service.factory;

import entities.Shop;
import entities.criteria.Criteria;
import entities.criteria.PriceSearchCriteria;
import java.io.FileNotFoundException;
import java.util.Set;
import service.discovery.ShopsDiscoverer;
import shoppinator.core.Shoppinator;
import shoppinator.core.ShoppinatorCore;

public class ShoppinatorFactory {

    ShopsDiscoverer shopsDiscoverer;

    public ShoppinatorFactory() {
        this.shopsDiscoverer = new ShopsDiscoverer();
    }

    public Shoppinator create(String path) throws FileNotFoundException {
        Set<Shop> shops = shopsDiscoverer.discover(path);
        ShoppinatorCore core = new ShoppinatorCore(shops);
        Criteria priceCriteria = new PriceSearchCriteria(core);

        return new Shoppinator(priceCriteria, core);
    }

}
