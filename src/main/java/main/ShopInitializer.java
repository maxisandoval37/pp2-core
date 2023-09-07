package main;

import domain.Shop;
import domain.ShopFactory;
import infrastructure.ShopCreator;
import java.util.Set;

public class ShopInitializer {

    Set<Shop> shops;
    ShopFactory shopFactory;

    public void init(String productName) {
        // busca busca busca shops con el discovery TODO
        // los crea y los guarda en shops
        this.shopFactory = new ShopCreator();

        Shop shop = shopFactory.create(productName);
        shop.searchProducts(productName);
    }
}
