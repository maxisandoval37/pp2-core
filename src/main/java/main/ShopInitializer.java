package main;

import domain.Shop;
import domain.ShopFactory;
import infrastructure.ShopCreator;
import infrastructure.ShopHandler;
import java.util.Set;

public class ShopInitializer {

    ShopHandler shopHandler;
    ShopFactory shopFactory;

    public ShopInitializer() {
        this.shopHandler = new ShopHandler();
        this.shopFactory = new ShopCreator();
    }

    public void init(String productName) {
        this.shopFactory = new ShopCreator();

        /*
        por ahora vamos a hacer que el factory devuelva un set de shops
        pero en realidad el discovery deberia devolver un set de clases
        y de ahí se llama a cada factory

        Set<Class> shopClasses = shopDiscoverer.discover();
        for (Class clss : shopClasses) {
            ShopFactory shopFactory = new ShopFactory(clss);
            Shop shop = shopFactory.create(productName);
            this.shopHandler.addShop(shop);
         }

         y ahí el shopHandler se encarga de notificar a los observers
         */

        Set<Shop> shops = shopFactory.create(productName);

        this.shopHandler.setShops(shops);
    }
}
