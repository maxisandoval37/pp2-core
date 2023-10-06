package pipeandfilter.architecture;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shoppinator.core.model.Product;

import java.util.List;

@AllArgsConstructor
@Slf4j
public class Pump extends Filter {

    private List<Product> data;

    @Override
    public void run() {

        try {
            String json = productsToJson(data);
            out.write(json);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

}