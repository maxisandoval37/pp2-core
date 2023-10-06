package shoppinator.core.model;

import lombok.Data;

@Data
public class SearchCriteria {

    private String productName;
    private Long minPrice;
    private Long maxPrice;
    private String[] selectedShops;

    public SearchCriteria(String productName, Long minPrice, Long maxPrice, String[] selectedShops) {
        this.productName = productName;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.selectedShops = selectedShops;
    }

    public SearchCriteria(String productName) {
        this.productName = productName;
        this.minPrice = 0L;
        this.maxPrice = Long.MAX_VALUE;
    }
}
