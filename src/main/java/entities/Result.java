package entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {

    private String name;
    private String postUrl;
    private Long price;
    private String productImageUrl;
}
