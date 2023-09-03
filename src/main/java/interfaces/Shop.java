package interfaces;

import java.util.List;
import lombok.Getter;
import models.Product;

@Getter
public abstract class Shop {

    String name;
    String url;

    public Shop(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public abstract List<Product> products(String productName);
}
