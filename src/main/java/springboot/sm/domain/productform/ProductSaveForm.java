package springboot.sm.domain.productform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSaveForm {

    private int productId;
    private MultipartFile productImage;
    private String productName;
    private String productContents;
    private int price;
    private int quantity;
    private String category;
}
