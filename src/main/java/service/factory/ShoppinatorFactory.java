package service.factory;

import entities.Shop;
import java.io.FileNotFoundException;
import java.util.Set;
import service.discovery.ShopsDiscoverer;
import shoppinator.core.Shoppinator;

public class ShoppinatorFactory {

    ShopsDiscoverer shopsDiscoverer;

    public ShoppinatorFactory() {
        this.shopsDiscoverer = new ShopsDiscoverer();
    }

    public Shoppinator create(String path) throws FileNotFoundException {
        Set<Shop> shops = shopsDiscoverer.discover(path);
        Shoppinator shoppinator = new Shoppinator(shops);

        // la app inicia con productos destacados
        shoppinator.search("mouse");
        return shoppinator;
    }

}
