package shoppinator.core.model;

import lombok.Data;

@Data
public class SearchCriteria {

    private static final Long MIN_PRICE = 0L;
    private static final Long MAX_PRICE = Long.MAX_VALUE;

    private String productName;
    private Long priceMin;
    private Long priceMax;
    private String[] selectedShops;

    public SearchCriteria(String productName, Long priceMin, Long priceMax, String[] selectedShops) {
        this.productName = productName;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.selectedShops = selectedShops;
    }

    public SearchCriteria(String productName) {
        this.productName = productName;
        this.priceMin = MIN_PRICE;
        this.priceMax = MAX_PRICE;
    }
}
