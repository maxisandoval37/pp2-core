package entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {

    private String name;
    private String shopName;
    private Long price;
    private String postUrl;
    private String productImageUrl;
}
