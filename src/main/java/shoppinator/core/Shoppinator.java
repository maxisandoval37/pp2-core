package shoppinator.core;

import entities.Result;
import entities.criteria.ShopsSelectionCriteria;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Observer;
import java.util.Set;
import java.util.stream.Collectors;
import service.factory.SearchCriteriaFactory;
import entities.Shop;
import entities.criteria.SearchCriteria;

public class Shoppinator {

    private final ShoppinatorCore core;
    private final ShopsContainer shopsContainer;

    private final SearchCriteriaFactory searchCriteriaFactory;
    private SearchCriteria criteria;
    private SearchCriteria.Memento lastSearchCriteria;

    public Shoppinator(ShoppinatorCore shoppinatorCore, ShopsContainer shopsContainer) {
        this.shopsContainer = shopsContainer;
        this.searchCriteriaFactory = new SearchCriteriaFactory();
        this.core = shoppinatorCore;
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
    public List<Result> search(Map<String, Object> params) throws IllegalArgumentException {
        createAndSaveCriteria(params);

        return loadShopsAndPerformSearch(criteria);
    }

    /**
     * Perform a new search with the last search parameters.
     */
    public List<Result> refresh() {
        criteria.restoreCriteria(lastSearchCriteria);

        return loadShopsAndPerformSearch(criteria);
    }

    /**
     * Returns the list of products found in the last search.
     */
    public List<Result> getSearchResult() {
        return core.getSearchResult();
    }

    /*
     * Returns the shops.
     */
    public Set<Shop> getShops() {
        return core.getShops();
    }

    /**
     * Subscribes an observer to the update of the core product list.
     */
    public void addObserver(Observer observer) {
        core.addObserver(observer);
    }

    private void createAndSaveCriteria(Map<String, Object> params) throws IllegalArgumentException {
        this.criteria = searchCriteriaFactory.create(params);
        this.lastSearchCriteria = criteria.saveState();
    }

    private List<Result> loadShopsAndPerformSearch(SearchCriteria criteria) {
        loadShops(criteria.getShopsSelectionCriteria());

        return core.search(this.criteria);
    }

    private void loadShops(ShopsSelectionCriteria criteria) {
        Set<Shop> selectedShops = Arrays.stream(criteria.getSelectedShops())
                .map(shopsContainer::resolveShop)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        core.setShops(selectedShops);
    }

}
