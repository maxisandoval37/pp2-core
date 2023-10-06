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

        searchCriteria.setProductName((params.length > 1) ? params[1] : null);

        FilterCriteria filterCriteria = new FilterCriteria();
        filterCriteria.setMinPrice((params.length > 2) ? Long.parseLong(params[2]) : DEFAULT_MIN_VALUE);
        filterCriteria.setMaxPrice((params.length > 3) ? Long.parseLong(params[3]) : DEFAULT_MAX_VALUE);

        if (filterCriteria.getMinPrice() != null && filterCriteria.getMaxPrice() != null
            && filterCriteria.getMinPrice() > filterCriteria.getMaxPrice()) {
            throw new IllegalArgumentException("The minimum price cannot be greater than the maximum price.");
        }

        DiscoverCriteria discoverCriteria = new DiscoverCriteria();
        discoverCriteria.setPath((params.length > 0) ? params[0] : null);
        discoverCriteria.setSelectedShops((params.length > 4) ? Arrays.copyOfRange(params, 4, params.length) : null);

        searchCriteria.setFilterCriteria(filterCriteria);
        searchCriteria.setDiscoverCriteria(discoverCriteria);

        return searchCriteria;
    }
}
