package springboot.sm.domain;

import lombok.Data;

@Data
public class Basket {

    private int cartId;
    private String loginId;
    private int productId;
    private String storeImageName;
    private String color;
    private String size;
    private int quantity;
    private int price;
    private int totalPrice;
}
