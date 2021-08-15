package springboot.sm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springboot.sm.domain.Basket;
import springboot.sm.domain.Member;
import springboot.sm.domain.Product;
import springboot.sm.domain.basketform.GetBasket;
import springboot.sm.service.BasketService;
import springboot.sm.service.ProductService;
import springboot.sm.web.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class BasketController {

    @Autowired
    private ProductService productService;
    @Autowired
    private BasketService basketService;

    @GetMapping("/basket")
    public String allBasket(Model model, HttpServletRequest request){
        List<Basket> basketAll = basketService.findBasketAll();
        List<Basket> selectBasket = new ArrayList<>();
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        int totalPrice = 0;
        for (Basket basket : basketAll) {
            if (basketService.findLoginIdByCartId(basket.getCartId()).equals(loginMember.getLoginId())){
                totalPrice += basket.getPrice() * basket.getCount();
                selectBasket.add(basket);
            }
        }
        model.addAttribute("loginMember",loginMember);
        model.addAttribute("totalPrice",totalPrice);
        model.addAttribute("basket",selectBasket);
        return "basket/basket";
    }

    @PostMapping("/basket/addBasket/{productId}")
    public String basketAdd(@PathVariable int productId, @RequestBody GetBasket form, HttpServletRequest request, Model model){
        log.info("basketForm={}",form);
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Product product = productService.findProduct(productId);
        Basket basket = new Basket();
        basket.setLoginId(loginMember.getLoginId());
        basket.setProductId(productId);
        basket.setProductName(product.getProductName());
        basket.setStoreImageName(product.getProductImage().getStoreImageName());
        basket.setColor(form.getColor());
        basket.setSize(form.getSize());
        basket.setPrice(product.getPrice());
        basket.setCount(form.getCount());
        basket.setCalSum(form.getCount()*product.getPrice());
        basketService.addBasket(basket);
        model.addAttribute("basket",basket);
        return "basket/basket";
    }

    @GetMapping("/basket/delete/{cartId}")
    public String getCartId(@PathVariable int cartId){
        log.info("cartId={}",cartId);
        basketService.deleteBasket(cartId);
        return "redirect:/basket";
    }

    @GetMapping("/basket/delete")
    public String allDelete(){
        basketService.deleteAllBasket();
        return "redirect:/basket";
    }
}
