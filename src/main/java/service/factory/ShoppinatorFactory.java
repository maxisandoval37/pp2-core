package service.factory;

import entities.Shop;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
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

    public Shoppinator create(String path) throws FileNotFoundException {
        Set<Shop> shops = shopsDiscoverer.discover(path);
        Shoppinator shoppinator = new Shoppinator(shops);
        Map<String, Object> initialParams = getInitialParams(featuredProduct, shops);

        // la app inicia con productos destacados
        shoppinator.search(initialParams);
        return shoppinator;
    }

    private Map<String, Object> getInitialParams(String featuredProduct, Set<Shop> shops) {
        Map<String, Object> params = new HashMap<>();
        params.put("productName", featuredProduct);
        params.put("selectedShops", shops.stream().map(Shop::getName).toArray(String[]::new));
        return params;
    }

}
