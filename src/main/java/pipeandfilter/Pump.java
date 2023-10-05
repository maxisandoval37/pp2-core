package pipeandfilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import shoppinator.core.model.Product;

import java.util.List;

public class Pump extends Filter {

    final private List<Product> data;
    public Pump(List<Product> data) {
        this.data = data;
    }

    @Override
    public void run() {

        try {
            String json = productsToJson(data);
            out.write(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}