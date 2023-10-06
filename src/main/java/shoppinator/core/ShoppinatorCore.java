package shoppinator.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppinator.core.interfaces.Shop;
import shoppinator.core.model.Product;
import shoppinator.core.model.criteria.SearchCriteria;

@SuppressWarnings("deprecation")
@Getter
@NoArgsConstructor
public class ShoppinatorCore extends Observable implements Observer {

    List<Product> products;
    Set<Shop> shops;

    public List<Product> search(SearchCriteria criteria) {
        this.products = new ArrayList<>();

        for (Shop shop : this.shops) {
            shop.search(criteria);
        }

        return this.products;
    }

    @Override
    public void update(Observable o, Object productList) {
        this.products.addAll((List<Product>) productList);
        products.sort(Comparator.comparing(p -> p.getProductPresentation().getPrice()));
        sendNotification();
    }

    public boolean sendNotification() {
        setChanged();
        super.notifyObservers(this.products);
        return hasChanged();
    }

    public void setShops(Set<Shop> shops) {
        this.shops = shops;
        this.addObservers();
    }

    private void addObservers() {
        for (Shop shop : this.shops) {
            shop.addObserver(this);
        }
    }
}
