package shoppinator.core;

import entities.Result;
import entities.criteria.Searchable;
import java.util.Collections;
import java.util.List;
import java.util.Observer;
import java.util.Set;
import entities.Shop;

public class Shoppinator {

    private final Searchable searcher;

    public Shoppinator(Searchable searcher) {
        this.searcher = searcher;
    }

    /**
     * Searches for products based on certain parameters.
     *
     * @param params A map of product search parameters.
     *                  <p>Parameters include:</p>
     *                  <ul>
     *                      <li>{@code "productName"} (String, Required): The product name to search for.</li>
     *                      <li>{@code "minPrice"} (Long, Optional): The minimum price to filter by.</li>
     *                      <li>{@code "maxPrice"} (Long, Optional): The maximum price to filter by.</li>
     *                      <li>{@code "selectedShops"} (String[], At least 1 required): An array of selected shops.</li>
     *                  </ul>
     */
    public List<Result> search(String params) throws IllegalArgumentException {
        return searcher.search(params);
    }

    /*
     * Returns the shops.
     */
    public Set<Shop> getShops() {
        // TODO ver cómo mierda meter esto
        return Collections.emptySet();
    }

    /**
     * Subscribes an observer to the update of the core product list.
     */
    public void addObserver(Observer observer) {
        // TODO ver cómo mierda meter esto
    }

}
