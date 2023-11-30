package entities;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    private String name;
    private String shop;
    private BigDecimal price;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!Article.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        final Article other = (Article) obj;

        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }

        if ((this.shop == null) ? (other.shop != null) : !this.shop.equals(other.shop)) {
            return false;
        }

        if ((this.price == null) ? (other.price != null) : !(this.price.compareTo(other.price) == 0)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 53 * hash + (this.shop != null ? this.shop.hashCode() : 0);
        hash = 53 * hash + (this.price != null ? this.price.hashCode() : 0);
        return hash;
    }
}
