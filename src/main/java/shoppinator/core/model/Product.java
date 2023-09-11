package shoppinator.core.model;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Product {
    private String name;
    private String postUrl;
    private ProductPresentation productPresentation;
}