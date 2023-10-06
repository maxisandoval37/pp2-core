package shoppinator.core.model.criteria;

import lombok.Data;

/**
 * This class is responsible for holding all the parameters
 * that the client passes, which is used in various parts of
 * the application.
 */
@Data
public class SearchCriteria {

    private String productName;
    // composition over inheritance
    private FilterCriteria filterCriteria;
    private DiscoverCriteria discoverCriteria;

}
