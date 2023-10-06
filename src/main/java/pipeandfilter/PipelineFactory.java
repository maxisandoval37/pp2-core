package pipeandfilter;

import java.util.List;
import pipeandfilter.architecture.Filter;
import pipeandfilter.architecture.Pipeline;
import pipeandfilter.architecture.PriceFilter;
import pipeandfilter.architecture.Pump;
import pipeandfilter.architecture.Sink;
import shoppinator.core.model.Product;
import shoppinator.core.model.SearchCriteria;

public class PipelineFactory {

    public Pipeline create(SearchCriteria criteria, List<Product> products) {
        Filter[] filters = {
            new Pump(products),
            new PriceFilter(criteria.getMinPrice(), criteria.getMaxPrice()),
            new Sink()
        };

        return new Pipeline(filters);
    }

}
