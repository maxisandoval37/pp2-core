package stubs;

import java.util.ArrayList;
import java.util.List;
import shoppinator.core.interfaces.ShoppinatorFacade;
import shoppinator.core.model.Product;
import shoppinator.core.model.criteria.SearchCriteria;

public class ShoppinatorRefreshableFacadeTestImpl implements ShoppinatorFacade {

    private int callCounter;
    private List<Product> currentProductList;
    private List<Product> initialProductList;
    private List<Product> updatedProductList;
    private final String productToSearch;

    public ShoppinatorRefreshableFacadeTestImpl(String productToSearch) {
        this.productToSearch = productToSearch;
        this.currentProductList = new ArrayList<>();
        callCounter = 1;
    }

    /**
     * <ul>
     * <li>Test implementation of products search, that only returns products
     * if the product name is the same as the one passed in the constructor.</li>
     * <li>This method returns a different number of products if it is called
     * successively.</li>
     * <li>This implementation is used to test the refresh functionality.</li>
     */
    @Override
    public List<Product> searchProductsInShops(SearchCriteria criteria) {
        currentProductList.clear();
        if (!productToSearch.equals(criteria.getProductName())) {
            return currentProductList;
        }

        updateCurrentProductList();
        callCounter++;
        return currentProductList;
    }

    private void updateCurrentProductList() {
        currentProductList.add(new Product("aa", "", null));
        currentProductList.add(new Product("ab", "", null));
        currentProductList.add(new Product("ac", "", null));

        if (callCounter % 2 == 0) {
            currentProductList.add(new Product("ad", "", null));
        }
    }

    @Override
    public List<Product> getCurrentProductList() {
        return currentProductList;
    }

    @Override
    public void subscribe(Object observer) {}
}
