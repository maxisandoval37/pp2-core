package shoppinator.core.model.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FilterCriteria {

    private Long minPrice;
    private Long maxPrice;

    public FilterCriteria() {
        this.minPrice = 0L;
        this.maxPrice = Long.MAX_VALUE;
    }
}
