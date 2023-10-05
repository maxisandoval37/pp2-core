package shoppinator.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class SearchCriteria {

    public SearchCriteria(String productName, Long priceMin, Long priceMax, String[] selectedShops) {
        this.productName = productName;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.selectedShops = selectedShops;
    }

    public SearchCriteria(String productName) {
        this.productName = productName;
    }

    private String productName;
    private Long priceMin;
    private Long priceMax;
    private String[] selectedShops;
}
