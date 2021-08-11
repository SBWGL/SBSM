package springboot.sm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private int productId;
    private UploadFile productImage;
    private String productName;

    private String productContents;
    private int price;
    private int quantity;

}
