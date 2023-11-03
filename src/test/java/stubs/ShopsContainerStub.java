package stubs;

import entities.Shop;
import shoppinator.core.ShopsContainer;

public class ShopsContainerStub implements ShopsContainer {

    @Override
    public void registerShop(String identifier, Shop implementation) {

    }

    @Override
    public Shop resolveShop(String identifier) {
        return null;
    }
}
