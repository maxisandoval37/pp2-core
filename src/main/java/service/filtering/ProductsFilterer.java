package service.filtering;

import java.util.List;
import service.factory.PipelineFactory;
import entities.filtering.Pipeline;
import entities.filtering.Sink;
import entities.Product;
import entities.criteria.FilterCriteria;

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

    public List<Product> filter(FilterCriteria criteria, List<Product> products) {
        Pipeline pipeline = pipelineFactory.create(criteria, products);

        pipeline.run();

        Sink sink = (Sink) pipeline.getFilters()[pipeline.getFilters().length - 1];
        return sink.getFilteredProducts();
    }
}
