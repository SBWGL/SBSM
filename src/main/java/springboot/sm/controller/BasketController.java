package springboot.sm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BasketController {

    @GetMapping("/basket")
    public String basketPage(){
        return "products/basket";
    }

    @PostMapping("/basket")
    public String basketPage1(){
        return "products/basket";
    }
}
