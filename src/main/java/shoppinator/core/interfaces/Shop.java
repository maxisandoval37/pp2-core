package shoppinator.core.interfaces;

import java.util.List;
import java.util.Observable;
import lombok.Getter;
import shoppinator.core.model.Product;
import shoppinator.core.model.SearchCriteria;

@Getter
@SuppressWarnings("deprecation")
public abstract class Shop extends Observable {

    List<Product> products;

    public abstract List<Product> search(SearchCriteria criteria);

    protected void addProducts(List<Product> newProducts) {
        products = newProducts;

        this.sendNotification();
    }

    public void sendNotification() {
        setChanged();
        super.notifyObservers(this.products);
    }
}
