package models;

import lombok.Data;
import java.util.List;

@Data
public class Shop {

    String storeName;
    List<Product> productList;
    String shopUrlDomain;
    String shopUrlSearch;
}