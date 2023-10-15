package service.factory;

import java.util.List;
import entities.filtering.Filter;
import entities.filtering.Pipeline;
import entities.filtering.PriceFilter;
import entities.filtering.Pump;
import entities.filtering.Sink;
import entities.Product;
import entities.criteria.FilterCriteria;

public class PipelineFactory {

    public Pipeline create(FilterCriteria criteria, List<Product> products) {
        Filter[] filters = {
            new Pump(products),
            new PriceFilter(criteria.getMinPrice(), criteria.getMaxPrice()),
            new Sink()
        };

        return new Pipeline(filters);
    }

}
