package service.factory;

import entities.Shop;
import entities.criteria.Criteria;
import entities.criteria.PriceCriteria;
import entities.criteria.ShopsSelectionCriteria;
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
        // TODO dejar esto mejor (crear un CriteriaFactory y toda la bola)
        Set<Shop> shops = shopsDiscoverer.discover(path);
        ShoppinatorCore core = new ShoppinatorCore(shops);

        Criteria shopsSelectionCriteria = new ShopsSelectionCriteria(core);
        Criteria priceCriteria = new PriceCriteria(shopsSelectionCriteria);

        return new Shoppinator(priceCriteria);
    }

}
