package service.factory;

import entities.criteria.FilterCriteria;
import entities.criteria.SearchCriteria;
import entities.criteria.ShopsSelectionCriteria;
import java.util.Map;

public class SearchCriteriaFactory {

    static final Long DEFAULT_MIN_VALUE = 0L;
    static final Long DEFAULT_MAX_VALUE = Long.MAX_VALUE;

    public SearchCriteria create(Map<String, Object> params) throws IllegalArgumentException {
        SearchCriteria searchCriteria = new SearchCriteria();

        setProductName(params, searchCriteria);
        setFilterCriteria(params, searchCriteria);
        setShopsSelectionCriteria(params, searchCriteria);

        return searchCriteria;
    }

    private void setProductName(Map<String, Object> params, SearchCriteria searchCriteria) {
        if (params.containsKey("productName")) {
            searchCriteria.setProductName((String) params.get("productName"));
        } else {
            throw new IllegalArgumentException("Product name is required.");
        }
    }

    private void setFilterCriteria(Map<String, Object> params, SearchCriteria searchCriteria) {
        FilterCriteria filterCriteria = new FilterCriteria();
        if (params.containsKey("minPrice")) {
            filterCriteria.setMinPrice((Long) params.get("minPrice"));
        } else {
            filterCriteria.setMinPrice(DEFAULT_MIN_VALUE);
        }
        if (params.containsKey("maxPrice")) {
            filterCriteria.setMaxPrice((Long) params.get("maxPrice"));
        } else {
            filterCriteria.setMaxPrice(DEFAULT_MAX_VALUE);
        }
        searchCriteria.setFilterCriteria(filterCriteria);
    }

    private void setShopsSelectionCriteria(Map<String, Object> params, SearchCriteria searchCriteria) {
        ShopsSelectionCriteria shopsSelectionCriteria = new ShopsSelectionCriteria();
        if (params.containsKey("selectedShops") && isNotNullOrEmpty((String[]) params.get("selectedShops"))) {
            shopsSelectionCriteria.setSelectedShops((String[]) params.get("selectedShops"));
        } else {
            throw new IllegalArgumentException("At least one shop must be selected.");
        }
        searchCriteria.setShopsSelectionCriteria(shopsSelectionCriteria);
    }

    private boolean isNotNullOrEmpty(String[] array) {
        return array != null && array.length > 0;
    }

}
