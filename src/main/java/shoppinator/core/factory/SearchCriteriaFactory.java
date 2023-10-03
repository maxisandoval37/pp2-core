package shoppinator.core.factory;

import shoppinator.core.model.SearchCriteria;

public class SearchCriteriaFactory {

    public SearchCriteria create(String[] params) {
        String name = (params.length > 0) ? params[0] : null;
        String priceMin = (params.length > 1) ? params[1] : null;
        String priceMax = (params.length > 2) ? params[2] : null;

        return new SearchCriteria(name, priceMin, priceMax);
    }
}
