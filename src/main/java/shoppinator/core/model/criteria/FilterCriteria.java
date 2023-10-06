package shoppinator.core.model.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FilterCriteria {

    private Long minPrice;
    private Long maxPrice;
}
