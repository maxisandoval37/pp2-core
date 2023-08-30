package models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductPresentation {
    private Float price;
    private String productImageUrl;
}