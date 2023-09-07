package infrastructure;

import domain.ProductSearcher;
import domain.Shop;
import java.util.List;
import lombok.SneakyThrows;
import models.Product;

public class ConcreteShop implements Shop {

    String name;
    List<Product> products;
    ProductSearcher productSearcher;

    @SneakyThrows
    @Override
    public void searchProducts(String productName) {
        productSearcher.search(productName);
    }
}
