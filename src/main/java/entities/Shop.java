package entities;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import lombok.Getter;

@Getter
@SuppressWarnings("deprecation")
public abstract class Shop extends Observable {

    public String name;

    public abstract Set<Map<String, BigDecimal>> search(String productName);

    protected void notifySearchResult(Set<Map<String, BigDecimal>> newProducts) {
        setChanged();
        super.notifyObservers(newProducts);
    }
}
