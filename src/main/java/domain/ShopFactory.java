package domain;

import java.util.Set;

public interface ShopFactory {

    Set<Shop> create(String productName);
}
