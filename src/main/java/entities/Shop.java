package entities;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public interface Shop {

    Map<String, BigDecimal> search(String productName);

    String getName();
}
