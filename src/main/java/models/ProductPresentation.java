package models;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * The intent of this class is to
 * present the product in the UI
 */
@ToString
@Getter
@AllArgsConstructor
public class ProductPresentation {

    private BigDecimal price;
    private ShopProduct shopProduct;
    private ProductImage productImage;
}
