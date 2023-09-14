package shoppinator.core.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String name;
    private String postUrl;
    private ProductPresentation productPresentation;
}