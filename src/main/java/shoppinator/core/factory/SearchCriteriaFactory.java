package shoppinator.core.factory;

import shoppinator.core.model.SearchCriteria;

public class SearchCriteriaFactory {

    public SearchCriteria create(String[] params) {
        String name = (params.length > 0) ? params[0] : null;
        Long priceMin = (params.length > 1) ? Long.parseLong(params[1]) : null;
        Long priceMax = (params.length > 2) ? Long.parseLong(params[2]) : null;
        String[] shopNames = new String[params.length - 3];

        return new SearchCriteria(name, priceMin, priceMax, shopNames);
    }
}
