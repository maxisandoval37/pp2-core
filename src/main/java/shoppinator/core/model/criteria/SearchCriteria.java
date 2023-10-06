package shoppinator.core.model.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is responsible for holding all the parameters
 * that the client passes, which is used in various parts of
 * the application.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {

    private String productName;
    // composition over inheritance
    private FilterCriteria filterCriteria;
    private DiscoverCriteria discoverCriteria;

    public Memento saveState() {
        return new Memento(this);
    }

    public void restoreCriteria(Memento memento) {
        this.productName = memento.getSearchCriteria().getProductName();
        this.filterCriteria = memento.getSearchCriteria().getFilterCriteria();
        this.discoverCriteria = memento.getSearchCriteria().getDiscoverCriteria();
    }

    public static class Memento {
        private final String productName;
        private final FilterCriteria filterCriteria;
        private final DiscoverCriteria discoverCriteria;

        public Memento(SearchCriteria searchCriteria) {
            this.productName = searchCriteria.getProductName();
            this.filterCriteria = searchCriteria.getFilterCriteria();
            this.discoverCriteria = searchCriteria.getDiscoverCriteria();
        }

        public SearchCriteria getSearchCriteria() {
            return new SearchCriteria(productName, filterCriteria, discoverCriteria);
        }
    }
}
