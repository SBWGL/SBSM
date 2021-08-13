package springboot.sm.domain.productform;

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
    private int price;
    private int quantity;
    private String category;
}
