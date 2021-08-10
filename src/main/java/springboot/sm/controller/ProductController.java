package springboot.sm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springboot.sm.domain.productform.ProductSaveForm;
import springboot.sm.domain.Product;
import springboot.sm.domain.UploadFile;
import springboot.sm.file.FileStore;
import springboot.sm.service.ProductService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Controller
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    FileStore fileStore;

    @GetMapping("/products")
    public String products(Model model){
        List<Product> products = productService.products();
        log.info("products={}",products);
        model.addAttribute("products",products);
        return "products/products";
    }

    @GetMapping("/detailProduct")
    public String detailProduct(){
        return "products/detailProduct";
    }

    @GetMapping("/addProduct")
    public String addProductForm(@ModelAttribute Product product){
        return "products/addProduct";
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute("product") ProductSaveForm form, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) return "products/addProduct";
        UploadFile imageFile = fileStore.storeFile(form.getProductImage());
        Product product = new Product();
        product.setProductId(form.getProductId());
        product.setProductName(form.getProductName());
        product.setProductContents(form.getProductContents());
        product.setProductImage(imageFile);
        product.setPrice(form.getPrice());
        product.setQuantity(form.getQuantity());
        log.info("product={}",product);
        productService.addProduct(product);
        return "redirect:/products";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource getImage(@PathVariable String filename) throws MalformedURLException {
        log.info("filename={}",filename);
        UrlResource resource = new UrlResource("file:///" + fileStore.getFullPath(filename));
        return resource;
    }
}
