package shoppinator.core;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;
import shoppinator.core.facade.ShoppinatorFacadeImpl;
import shoppinator.core.factory.SearchCriteriaFactory;
import shoppinator.core.interfaces.Shop;
import shoppinator.core.interfaces.ShoppinatorFacade;
import shoppinator.core.model.Product;
import shoppinator.core.model.criteria.SearchCriteria;
import utils.PropertiesHelper;

public class Shoppinator {

    private ShoppinatorFacade facade;
    private String featuredProduct;

    private SearchCriteriaFactory searchCriteriaFactory;
    private SearchCriteria criteria;
    private SearchCriteria.Memento lastSearchCriteria;

    public Shoppinator() {
        facade = new ShoppinatorFacadeImpl();
        searchCriteriaFactory = new SearchCriteriaFactory();
        this.featuredProduct = PropertiesHelper.getValue("featured.product");
    }

    public Shoppinator(ShoppinatorFacade shoppinatorFacade) {
        facade = shoppinatorFacade;
        searchCriteriaFactory = new SearchCriteriaFactory();
        this.featuredProduct = PropertiesHelper.getValue("featured.product");
    }

    /**
     * Initializes the core, loading the shops in the given path and searching for the featured product.
     */
    public void init(String path) throws FileNotFoundException {
        createAndSaveCriteria(path, featuredProduct);

        facade.searchProductsInShops(criteria);
    }

    /**
     * Searches for products based on certain criteria.
     *
     * @param params Product search parameters.
     *               <ol>
     *                  <li>The first parameter is the path to the plugins folder. (Required)</li>
     *                  <li>The second parameter is the product name. (Required)</li>
     *                  <li>The third parameter is the minimum price. (Optional)</li>
     *                  <li>The fourth parameter is the maximum price. (Optional)</li>
     *                  <li>The remaining parameters are the selected shops. (At least 1 required)</li>
     *               </ol>
     */
    public List<Product> search(String... params) throws FileNotFoundException {
        createAndSaveCriteria(params);

        return facade.searchProductsInShops(criteria);
    }

    /**
     * Perform a new search with the last search parameters.
     */
    public List<Product> refresh() throws FileNotFoundException {
        criteria.restoreCriteria(lastSearchCriteria);

        return facade.searchProductsInShops(criteria);
    }

    /**
     * Returns the list of products found in the last search.
     */
    public List<Product> getProductList() {
        return facade.getCurrentProductList();
    }

    /*
     * Returns the list of shops.
     */
    public Set<Shop> getShops() {
        return facade.getShops();
    }

    /**
     * Subscribes an observer to the update of the core product list.
     */
    public void subscribe(Object observer) {
        facade.subscribe(observer);
    }

    private void createAndSaveCriteria(String... params) {
        this.criteria = searchCriteriaFactory.create(params);
        this.lastSearchCriteria = criteria.saveState();
    }
}
