package shoppinator.core;

import shoppinator.core.interfaces.Shop;
import java.util.List;
import shoppinator.core.model.Product;

public class ShopProxy extends Shop {

    ShopScraper shopScraper;

    public ShopProxy(ShopScraper shopScraper) {
        this.shopScraper = shopScraper;
    }

    @Override
    public List<Product> search(String productName) {
        return this.shopScraper.search(productName);
    }

}
