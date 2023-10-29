package shoppinator.core;

import entities.Product;
import entities.Result;
import entities.Shop;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import lombok.Getter;
import service.assembly.ResultAssembler;

@SuppressWarnings("deprecation")
public class Shoppinator extends Observable implements Observer {

    @Getter
    List<Result> searchResult;
    @Getter
    Set<Shop> shops;

    private final ResultAssembler resultAssembler;
    private Set<Product> domainProducts;

    public Shoppinator(Set<Shop> shops) {
        this.setShops(shops);
        this.domainProducts = new HashSet<>();
        this.searchResult = new ArrayList<>();
        this.resultAssembler = new ResultAssembler();
    }

    public List<Result> search(String productName) {
        this.domainProducts.clear();
        this.searchResult.clear();

        for (Shop shop : this.shops) {
            shop.search(productName);
        }

        return this.searchResult;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void update(Observable o, Object products) {
        this.domainProducts.addAll((Set<Product>) products);
        this.searchResult = resultAssembler.assembly(domainProducts);

        setChanged();
        super.notifyObservers(this.searchResult);
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
