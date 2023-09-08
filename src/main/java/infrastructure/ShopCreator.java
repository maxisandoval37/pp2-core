package infrastructure;

import domain.Shop;
import domain.ShopFactory;
import java.util.Set;

public class ShopCreator implements ShopFactory {

    @Override
    public Set<Shop> create(String productName) {
        return Set.of(new ConcreteShop(productName));
    }
}
