package stubs;

import entities.Result;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observer;
import java.util.Set;
import entities.Shop;
import shoppinator.core.ShoppinatorCore;
import entities.criteria.SearchCriteria;

/**
 * <ul>
 * <li>Test implementation of shoppinator, that only returns products
 * if the product name is the same as the one passed in the constructor.</li>
 * <li>The search method returns a different list of products if it is called
 * successively.</li>
 * <li>This implementation is used to test the refresh functionality.</li>
 */
public class ShoppinatorRefreshableStub implements ShoppinatorCore {

    private int callCounter;
    private List<Result> currentSearchResult;
    private final List<Result> initialSearchResult;
    private final List<Result> refreshedSearchResult;
    private final String productToSearch;

    public ShoppinatorRefreshableStub(String productToSearch, List<Result> initialSearchResult,
        List<Result> refreshedProductList) {
        this.currentSearchResult = new ArrayList<>();
        this.initialSearchResult = initialSearchResult;
        this.refreshedSearchResult = refreshedProductList;
        this.productToSearch = productToSearch;
        this.callCounter = 1;
    }

    @Override
    public List<Result> search(SearchCriteria criteria) {
        currentSearchResult.clear();
        if (!productToSearch.equals(criteria.getProductName())) {
            return currentSearchResult;
        }

        updateProductList();
        return currentSearchResult;
    }

    @Override
    public List<Result> getSearchResult() {
        return currentSearchResult;
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

    private void updateProductList() {
        if (callCounter % 2 == 0) {
            this.currentSearchResult = new ArrayList<>(refreshedSearchResult);
        } else {
            this.currentSearchResult = new ArrayList<>(initialSearchResult);
        }

        callCounter++;
    }
}
