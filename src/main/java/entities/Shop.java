package entities;

import java.util.Observable;
import java.util.Set;
import lombok.Getter;

@Getter
@SuppressWarnings("deprecation")
public abstract class Shop extends Observable {

    public abstract Set<Product> search(String productName);

    protected void notifySearchResult(Set<Product> newProducts) {
        setChanged();
        super.notifyObservers(newProducts);
    }
}
