package springboot.sm.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import springboot.sm.domain.Basket;

import java.util.List;

@Mapper
@Repository
public interface BasketMapper {

    List<Basket> findBasketAll();
    void addBasket(Basket basket);
    void deleteBasket(int cartId);
    void deleteAllBasket();
    String findLoginIdByCartId(int cartId);
}
