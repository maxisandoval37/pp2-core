package service;

import java.util.List;
import pipeandfilter.PipelineFactory;
import pipeandfilter.architecture.Pipeline;
import pipeandfilter.architecture.Sink;
import shoppinator.core.model.Product;
import shoppinator.core.model.SearchCriteria;

/*
 * This class is responsible for creating a pipeline of filters
 * and running it, to hide the complexity of the pipeline from
 * the client, acting as a Facade.
 */
public class ProductsFilterer {

    private final PipelineFactory pipelineFactory;

    public ProductsFilterer() {
        this.pipelineFactory = new PipelineFactory();
    }

    // TODO en este caso no sería searchCriteria sino filterCriteria, quizas
    //      podriamos crear una interfaz marker Criteria y que SearchCriteria
    //      y FilterCriteria la implementen
    public List<Product> filter(SearchCriteria criteria, List<Product> products) {
        Pipeline pipeline = pipelineFactory.create(criteria, products);

        pipeline.run();

        Sink sink = (Sink) pipeline.getFilters()[pipeline.getFilters().length - 1];
        return sink.getFilteredProducts();
    }
}
