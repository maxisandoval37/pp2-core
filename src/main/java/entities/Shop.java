package entities;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public interface Shop {

    Map<String, BigDecimal> search(String productName);

    // INB4 https://stackoverflow.com/questions/1130294/java-interface-usage-guidelines-are-getters-and-setters-in-an-interface-bad
    String getName();
}
