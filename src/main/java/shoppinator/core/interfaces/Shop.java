package shoppinator.core.interfaces;

import java.util.List;
import java.util.Observable;
import lombok.Getter;
import shoppinator.core.model.Product;

@Getter
public abstract class Shop extends Observable {

    List<Product> products;

    public abstract List<Product> search(String productName);

    public void addProduct(Product product) {
        products.add(product);
        this.sendNotification();
    }

    public void sendNotification() {
        setChanged();
        super.notifyObservers();
    }
}
