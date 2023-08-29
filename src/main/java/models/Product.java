package models;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Product {
    private String name;
    private Float price;
    private String postUrl;
    private String imgUrl;
}