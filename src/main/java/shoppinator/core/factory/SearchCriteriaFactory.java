package shoppinator.core.factory;

import java.util.Arrays;
import shoppinator.core.model.SearchCriteria;

public class SearchCriteriaFactory {

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
        if(params.length == 1) {
            return new SearchCriteria(params[0]);
        }

        String name = (params.length > 1) ? params[1] : null;
        Long priceMin = (params.length > 2) ? Long.parseLong(params[2]) : null;
        Long priceMax = (params.length > 3) ? Long.parseLong(params[3]) : null;
        String[] shopNames = Arrays.copyOfRange(params, 4, params.length);

        return new SearchCriteria(name, priceMin, priceMax, shopNames);
    }
}
