package shoppinator.core;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;
import shoppinator.core.facade.ShoppinatorFacade;
import shoppinator.core.interfaces.Scraper;
import shoppinator.core.model.Product;

public class Shoppinator {

    ShoppinatorFacade facade;

    public Shoppinator() {
        facade = new ShoppinatorFacade();
    }

    /*
     * Initializes the core, loading the shops in the given path
     * and searching for the featured product.
     */
    public void init(String path) throws FileNotFoundException {
        facade.init(path);
    }

    /**
     * Searches for products based on certain criteria.
     *
     * @param params Product search parameters.
     *               <ol>
     *                  <li>The first parameter is the path to the plugins folder.</li>
     *                  <li>The second parameter is the product name.</li>
     *                  <li>The third parameter is the minimum price.</li>
     *                  <li>The fourth parameter is the maximum price.</li>
     *                  <li>The remaining parameters are the selected shops.</li>
     *               </ol>
     *               If no parameters are given, the last search parameters are used.
     */
    public List<Product> search(String... params) throws FileNotFoundException {
        return facade.search(params);
    }

    /*
     * Returns the list of products found in the last search.
     */
    public List<Product> getProductList() {
        return facade.getProductList();
    }

    /**
     * Subscribes an observer to the update of the core product list.
     */
    public void subscribe(Object observer) {
        facade.subscribe(observer);
    }

    /*
     * Returns the list of shops.
     */
    public Set<Scraper> getScrapers() { return facade.getScrapers(); }
}
