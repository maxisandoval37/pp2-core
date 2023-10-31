package shoppinator.core;

import entities.Result;
import entities.criteria.SearchCriteria;
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
public class ShoppinatorCoreImpl extends Observable implements ShoppinatorCore, Observer {

    @Getter
    List<Result> searchResult;
    @Getter
    Set<Shop> shops;

    private final ResultAssembler resultAssembler;
    private Set<Product> domainProducts;
    private SearchCriteria criteria;

    public ShoppinatorCoreImpl(Set<Shop> shops) {
        this.setShops(shops);
        this.domainProducts = new HashSet<>();
        this.searchResult = new ArrayList<>();
        this.resultAssembler = new ResultAssembler();
    }

    @Override
    public List<Result> search(SearchCriteria criteria) {
        this.domainProducts.clear();
        this.searchResult.clear();
        this.criteria = criteria;

        for (Shop shop : this.shops) {
            shop.search(criteria.getProductName());
        }

        return this.searchResult;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void update(Observable o, Object products) {
        this.domainProducts.addAll((Set<Product>) products);
        this.searchResult = resultAssembler.assembly(domainProducts, criteria.getFilterCriteria());

        setChanged();
        super.notifyObservers(this.searchResult);
    }

    @Override
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
