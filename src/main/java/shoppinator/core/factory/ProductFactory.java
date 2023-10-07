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
    private final ObjectMapper objectMapper;

    public ProductFactory() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }

    public List<Product> create(String productJson) {
        try {
            return this.objectMapper.readValue(productJson, new TypeReference<List<Product>>() {});
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        return Collections.emptyList();
    }
}
