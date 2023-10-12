package shoppinator.core.model.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FilterCriteria {

    private Long minPrice;
    private Long maxPrice;
}
