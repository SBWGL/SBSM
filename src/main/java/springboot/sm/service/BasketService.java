package springboot.sm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.sm.domain.Basket;
import springboot.sm.mapper.BasketMapper;

import java.util.List;

@Service
@Slf4j
public class BasketService {

    @Autowired
    private BasketMapper basketMapper;

    public List<Basket> findBasketAll(){
        return basketMapper.findBasketAll();
    }

    public void addBasket(Basket basket){
        basketMapper.addBasket(basket);
    }

    public void deleteBasket(int cartId){
        basketMapper.deleteBasket(cartId);
    }

    public void deleteAllBasket(){
        basketMapper.deleteAllBasket();
    }

    public String findLoginIdByCartId(int cartId){
        return basketMapper.findLoginIdByCartId(cartId);
    }
}
