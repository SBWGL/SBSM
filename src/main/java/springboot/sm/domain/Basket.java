package springboot.sm.domain;

import lombok.Data;

@Data
public class Basket {

    private int cartId;
    private String loginId;
    private int productId;
    private String productName;
    private String storeImageName;
    private String color;
    private String size;
    private int count;
    private int price;
    private int calSum;
}
