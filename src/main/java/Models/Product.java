package Models;

import lombok.Data;

@Data
public class Product {
    private String name;
    private Double price;
    private String store;
    private String postUrl;
    private String imgUrl;
}