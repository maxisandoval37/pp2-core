package shoppinator.core;

import entities.Shop;

public interface ShopsContainer {

    void registerShop(String identifier, Shop implementation);

    Shop resolveShop(String identifier);
}
