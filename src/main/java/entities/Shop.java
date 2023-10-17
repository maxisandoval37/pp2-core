package entities;

import java.util.List;
import java.util.Observable;
import lombok.Getter;
import entities.criteria.SearchCriteria;

@Getter
@SuppressWarnings("deprecation")
public abstract class Shop extends Observable {

    List<Product> products;

    public abstract List<Product> search(SearchCriteria criteria);

    protected void addProducts(List<Product> newProducts) {
        products = newProducts;

        this.sendNotification();
    }

    public boolean sendNotification() {
        setChanged();
        super.notifyObservers(this.products);
        return hasChanged();
    }
}
