package infrastructure;

import domain.Shop;
import infrastructure.ProductSearcher;
import java.util.List;
import lombok.Getter;
import lombok.SneakyThrows;
import models.Product;

public class ConcreteShop implements Shop {

    String name;
    @Getter
    List<Product> products;
    ProductSearcher productSearcher;

    public ConcreteShop(String productName) {
        this.name = "Concrete";
        this.productSearcher = new ProductSearcher();

        this.searchProducts(productName);
    }

    @Override
    public void searchProducts(String productName) {
        this.products = productSearcher.search(productName);
    }

}
