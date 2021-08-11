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
        model.addAttribute("products",products);
        return "products/products";
    }

    @GetMapping("/product/{productId}")
    public String detailProduct(@PathVariable int productId,Model model){
        Product product = productService.findProduct(productId);
        model.addAttribute("product",product);
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

    @GetMapping("/product/{productId}/edit")
    public String editProductForm(@PathVariable int productId, Model model){
        Product product = productService.findProduct(productId);
        model.addAttribute("product",product);
        return "products/editProduct";
    }

    @PostMapping("/product/{productId}/edit")
    public String editProduct(@PathVariable int productId, @ModelAttribute("product") ProductSaveForm form,
                              BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) return "products/editProduct";
        UploadFile imageFile = fileStore.storeFile(form.getProductImage());
        Product updateProduct = new Product();
        updateProduct.setProductId(productId);
        updateProduct.setProductName(form.getProductName());
        updateProduct.setProductContents(form.getProductContents());
        updateProduct.setPrice(form.getPrice());
        updateProduct.setQuantity(form.getQuantity());
        // 변동된 이미지가 없었을 경우 -> 기존의 이미지를 가져옴
        if (imageFile == null){
            UploadFile image = productService.findImage(productId);
            updateProduct.setProductImage(image);
        }else {
            updateProduct.setProductImage(imageFile);
        }
        productService.updateProduct(updateProduct);
        return "redirect:/product/{productId}";
    }

}
