package shoppinator.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCriteria {

    private String productName;
    private Long priceMin;
    private Long priceMax;
    private String[] selectedShops;
}
