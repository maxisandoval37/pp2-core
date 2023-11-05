package stubs;

import entities.Result;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observer;
import java.util.Set;
import entities.Shop;
import shoppinator.core.ShoppinatorCore;
import entities.criteria.Criteria;

/**
 * <ul>
 * <li>Test implementation of shoppinator, that only returns products
 * if the product name is the same as the one passed in the constructor.</li>
 * <li>The search method returns the same list of products if it is called
 * successively.</li>
 * <li>This implementation is used to test the refresh functionality.</li>
 */
public class ShoppinatorNonRefreshableStub implements ShoppinatorCore {

    private final List<Result> searchResult;
    private final String productToSearch;

    public ShoppinatorNonRefreshableStub(String aProduct) {
        this.productToSearch = aProduct;
        this.searchResult = new ArrayList<>();
        searchResult.add(new Result("aa", "", 0L, "", ""));
        searchResult.add(new Result("ab", "", 0L, "", ""));
        searchResult.add(new Result("ac", "", 0L, "", ""));
    }

    @Override
    public List<Result> search(Criteria criteria) {
        if (!criteria.getProductName().equals(productToSearch)) {
            return Collections.EMPTY_LIST;
        }

        return searchResult;
    }

    @Override
    public List<Result> getSearchResult() {
        return searchResult;
    }

    @Override
    public Set<Shop> getShops() {
        return Collections.EMPTY_SET;
    }

    @Override
    public void setShops(Set<Shop> shops) {
    }

    @Override
    public void addObserver(Observer observer) {
    }

}
