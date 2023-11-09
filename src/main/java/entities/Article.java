package entities;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Article {

    private String name;
    private String shop;
    private BigDecimal price;
}
