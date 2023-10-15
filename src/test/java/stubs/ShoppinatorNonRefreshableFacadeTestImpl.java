package stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import shoppinator.core.interfaces.Shop;
import shoppinator.core.interfaces.ShoppinatorFacade;
import shoppinator.core.model.Product;
import shoppinator.core.model.criteria.SearchCriteria;

/**
 * <ul>
 * <li>Test implementation of shoppinator facade, that only returns products
 * if the product name is the same as the one passed in the constructor.</li>
 * <li>The search method returns the same list of products if it is called
 * successively.</li>
 * <li>This implementation is used to test the refresh functionality.</li>
 */
public class ShoppinatorNonRefreshableFacadeTestImpl implements ShoppinatorFacade {

    private final List<Product> productList;
    private final String productToSearch;

    public ShoppinatorNonRefreshableFacadeTestImpl(String aProduct) {
        this.productToSearch = aProduct;
        this.productList = new ArrayList<>();
        productList.add(new Product("aa", "", null));
        productList.add(new Product("ab", "", null));
        productList.add(new Product("ac", "", null));
    }

    @Override
    public List<Product> searchProductsInShops(SearchCriteria criteria) {
        if (!criteria.getProductName().equals(productToSearch)) {
            return Collections.EMPTY_LIST;
        }

        return productList;
    }

    @Override
    public List<Product> getCurrentProductList() {
        return productList;
    }

    @Override
    public Set<Shop> getShops() {
        return Collections.EMPTY_SET;
    }

    @Override
    public void subscribe(Object observer) {}
}
