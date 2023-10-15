package stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import entities.Shop;
import shoppinator.core.ShoppinatorFacade;
import entities.Product;
import entities.criteria.SearchCriteria;

/**
 * <ul>
 * <li>Test implementation of shoppinator facade, that only returns products
 * if the product name is the same as the one passed in the constructor.</li>
 * <li>The search method returns a different list of products if it is called
 * successively.</li>
 * <li>This implementation is used to test the refresh functionality.</li>
 */
public class ShoppinatorRefreshableFacadeTestImpl implements ShoppinatorFacade {

    private int callCounter;
    private List<Product> currentProductList;
    private final List<Product> initialProductList;
    private final List<Product> refreshedProductList;
    private final String productToSearch;

    public ShoppinatorRefreshableFacadeTestImpl(String productToSearch, List<Product> initialProductList,
        List<Product> refreshedProductList) {
        this.currentProductList = new ArrayList<>();
        this.initialProductList = initialProductList;
        this.refreshedProductList = refreshedProductList;
        this.productToSearch = productToSearch;
        this.callCounter = 1;
    }

    @Override
    public List<Product> searchProductsInShops(SearchCriteria criteria) {
        currentProductList.clear();
        if (!productToSearch.equals(criteria.getProductName())) {
            return currentProductList;
        }

        updateProductList();
        return currentProductList;
    }

    @Override
    public List<Product> getCurrentProductList() {
        return currentProductList;
    }

    @Override
    public Set<Shop> getShops() {
        return Collections.EMPTY_SET;
    }

    @Override
    public void subscribe(Object observer) {}

    private void updateProductList() {
        if (callCounter % 2 == 0) {
            this.currentProductList = new ArrayList<>(refreshedProductList);
        } else {
            this.currentProductList = new ArrayList<>(initialProductList);
        }

        callCounter++;
    }
}
