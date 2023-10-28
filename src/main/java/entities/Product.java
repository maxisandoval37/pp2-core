package entities;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String name;
    private String shopName;

    @JsonUnwrapped
    private ProductPresentation productPresentation;
}