package shoppinator.core.interfaces;

import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import lombok.Getter;
import shoppinator.core.model.Product;

@Getter
@SuppressWarnings("deprecation")
public abstract class Shop extends Observable {

    List<Product> products;

    public abstract List<Product> search(String productName);

    protected void addProducts(List<Product> newProducts) {
        products = newProducts;
        products.sort(Comparator.comparing(p -> p.getProductPresentation().getPrice()));

        this.sendNotification();
    }

    public void sendNotification() {
        setChanged();
        super.notifyObservers(this.products);
    }
}
