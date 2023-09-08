package infrastructure;

import domain.Shop;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;
import lombok.Getter;

@Getter
public class ShopHandler extends Observable {

    Set<Shop> shops;

    public ShopHandler() {
        this.shops = new HashSet<>();
    }

    public void setShops(Set<Shop> shops) {
        this.shops = shops;
        sendNotification();
    }

    // para cuando implementemos el observer
    public void addShop(Shop shop) {
        this.shops.add(shop);
        sendNotification();
    }

    public void sendNotification() {
        this.setChanged();
        this.notifyObservers(shops);
    }
}
