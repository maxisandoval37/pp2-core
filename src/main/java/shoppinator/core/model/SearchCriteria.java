package shoppinator.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCriteria {

    private String productName;
    private Long priceMin;
    private Long priceMax;
    private String[] shopNames;

    public Memento saveState() {
        return new Memento(this);
    }

    public void restoreState(Memento memento) {
        this.productName = memento.getSearchCriteria().getProductName();
        this.priceMin = memento.getSearchCriteria().getPriceMin();
        this.priceMax = memento.getSearchCriteria().getPriceMax();
        this.shopNames = memento.getSearchCriteria().getShopNames();
    }

    public static class Memento {
        private final String productName;
        private final Long priceMin;
        private final Long priceMax;
        private final String[] shopNames;

        public Memento(SearchCriteria searchCriteria) {
            this.productName = searchCriteria.getProductName();
            this.priceMin = searchCriteria.getPriceMin();
            this.priceMax = searchCriteria.getPriceMax();
            this.shopNames = searchCriteria.getShopNames();
        }

        public SearchCriteria getSearchCriteria() {
            return new SearchCriteria(productName, priceMin, priceMax, shopNames);
        }
    }
}
