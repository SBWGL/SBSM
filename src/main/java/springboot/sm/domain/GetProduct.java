package springboot.sm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetProduct {

    private int productId;
    private String uploadImageName;
    private String storeImageName;
    private String productName;
    private String productContents;
    private int size;
    private String color;
    private int price;
    private int quantity;
}
