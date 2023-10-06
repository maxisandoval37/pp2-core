package shoppinator.core.factory;

import java.util.Arrays;
import shoppinator.core.model.criteria.DiscoverCriteria;
import shoppinator.core.model.criteria.FilterCriteria;
import shoppinator.core.model.criteria.SearchCriteria;

public class SearchCriteriaFactory {

    private static final Long DEFAULT_MIN_VALUE = 0L;
    private static final Long DEFAULT_MAX_VALUE = Long.MAX_VALUE;

    /**
     * Creates a SearchCriteria object from the given parameters.
     * @param params The parameters to create the SearchCriteria object.
     *               The first parameter is the path to the plugins folder.
     *               The second parameter is the product name.
     *               The third parameter is the minimum price.
     *               The fourth parameter is the maximum price.
     *               The rest of the parameters are the selected shops.
     *               If the first parameter is the only one, then it is the product name.
     */
    public SearchCriteria create(String[] params) {
        SearchCriteria searchCriteria = new SearchCriteria();

        setProductName(params, searchCriteria);
        setFilterCriteria(params, searchCriteria);
        setDiscoverCriteria(params, searchCriteria);

        return searchCriteria;
    }

    private void setProductName(String[] params, SearchCriteria searchCriteria) {
        if (params.length > 1) {
            searchCriteria.setProductName(params[1]);
        }
    }

    private void setFilterCriteria(String[] params, SearchCriteria searchCriteria) {
        FilterCriteria filterCriteria = new FilterCriteria();

        if (params.length > 2 && params[2] != null && !params[2].isEmpty()) {
            filterCriteria.setMinPrice(Long.parseLong(params[2]));
        } else {
            filterCriteria.setMinPrice(DEFAULT_MIN_VALUE);
        }

        if (params.length > 3 && params[3] != null && !params[3].isEmpty()) {
            filterCriteria.setMaxPrice(Long.parseLong(params[3]));
        } else {
            filterCriteria.setMaxPrice(DEFAULT_MAX_VALUE);
        }

        if (filterCriteria.getMinPrice() != null && filterCriteria.getMaxPrice() != null
            && filterCriteria.getMinPrice() > filterCriteria.getMaxPrice()) {
            throw new IllegalArgumentException("The minimum price cannot be greater than the maximum price.");
        }

        searchCriteria.setFilterCriteria(filterCriteria);
    }

    private void setDiscoverCriteria(String[] params, SearchCriteria searchCriteria) {
        DiscoverCriteria discoverCriteria = new DiscoverCriteria();

        if (params.length > 0) {
            discoverCriteria.setPath(params[0]);
        }

        if (params.length > 4) {
            discoverCriteria.setSelectedShops(Arrays.copyOfRange(params, 4, params.length));
        }

        searchCriteria.setDiscoverCriteria(discoverCriteria);
    }

}
