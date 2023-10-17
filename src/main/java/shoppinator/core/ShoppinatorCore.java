package shoppinator.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import lombok.Getter;
import entities.Shop;
import entities.Product;
import entities.criteria.SearchCriteria;

@SuppressWarnings("deprecation")
@Getter
public class ShoppinatorCore extends Observable implements Observer {

    private List<Product> products;
    private Set<Shop> shops;

    public ShoppinatorCore() {
        this.products = new ArrayList<>();
    }

    public List<Product> search(SearchCriteria criteria) {
        this.products.clear();

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
