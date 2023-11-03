package shoppinator.core;

import entities.Shop;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ShopsContainerImpl implements ShopsContainer {

    private final Map<String, Shop> shopInstances;

    public ShopsContainerImpl(Set<Shop> shops) {
        this.shopInstances = new HashMap<>();
        shops.forEach(shop -> shopInstances.put(shop.getName(), shop));
    }

    @Override
    public void registerShop(String identifier, Shop implementation) {
        shopInstances.put(identifier, implementation);
    }

    @Override
    public Shop resolveShop(String identifier) {
        return shopInstances.get(identifier);
    }
}
