package shoppinator.core;

import entities.Result;
import entities.criteria.Searchable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import lombok.Getter;
import entities.Shop;
import entities.Product;
import service.assembly.ResultAssembler;

@SuppressWarnings("deprecation")
@Getter
public class ShoppinatorCore extends Searchable implements Observer {

    List<Result> searchResult;
    @Getter
    Set<Shop> shops;

    private final ResultAssembler resultAssembler;
    private Set<Product> domainProducts;

    public ShoppinatorCore(Set<Shop> shops) {
        this.setShops(shops);
        this.domainProducts = new HashSet<>();
        this.searchResult = new ArrayList<>();
        this.resultAssembler = new ResultAssembler();
    }

    @Override
    public List<Result> search(String params) {
        this.domainProducts.clear();
        this.searchResult.clear();

        for (Shop shop : this.shops) {
            shop.search(params);
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
