package shoppinator.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCriteria {

    private String productName;
    private String priceMin;
    private String priceMax;
}
