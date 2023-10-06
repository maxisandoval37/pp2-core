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

    public Pipeline create(SearchCriteria criteria, List<Product> products, Sink sink) {
        // TODO está medio choto que tengamos que pasarle el sink
        //      como parametro para crear el pipeline, ver como
        //      podemos refactorizar esto
        Filter[] filters = {
            new Pump(products),
            new PriceFilter(criteria.getMinPrice(), criteria.getMaxPrice()),
            sink
        };

        return new Pipeline(filters);
    }

}
