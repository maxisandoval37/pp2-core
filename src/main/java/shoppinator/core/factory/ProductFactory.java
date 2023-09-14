package shoppinator.core.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import shoppinator.core.model.Product;

@Slf4j
public class ProductFactory {

    ObjectMapper objectMapper;

    public ProductFactory() {
        this.objectMapper = new ObjectMapper();
    }

    public List<Product> create(String productJson) {
        try {
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
            return this.objectMapper.readValue(productJson, new TypeReference<List<Product>>(){});
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        return Collections.emptyList();
    }
}
