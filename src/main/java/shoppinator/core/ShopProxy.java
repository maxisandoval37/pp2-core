package shoppinator.core;

import shoppinator.core.interfaces.Shop;
import java.util.Collections;
import java.util.List;
import shoppinator.core.model.Product;

public class ShopProxy extends Shop {

    ShopScrapper shopScrapper;

    public ShopProxy(ShopScrapper shopScrapper) {
        this.shopScrapper = shopScrapper;
    }

    @Override
    public List<Product> search(String productName) {
        return this.shopScrapper.search(productName);
    }

}
