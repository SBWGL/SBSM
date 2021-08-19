package springboot.sm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springboot.sm.domain.Member;
import springboot.sm.domain.Review;
import springboot.sm.domain.common.Criteria;
import springboot.sm.domain.common.PageMake;
import springboot.sm.domain.productform.GetProduct;
import springboot.sm.domain.productform.ProductSaveForm;
import springboot.sm.domain.Product;
import springboot.sm.domain.common.UploadFile;
import springboot.sm.file.FileStore;
import springboot.sm.service.ProductService;
import springboot.sm.web.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    FileStore fileStore;

    @GetMapping("/products/{category}")
    public String productsByCategory(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
            Model model, @ModelAttribute("cri") Criteria cri, @PathVariable String category){
        List<Product> listPaging = productService.getListPaging(cri, category);// 페이징된 상품 전체 가져오기
        model.addAttribute("products",listPaging);
        int total = productService.getTotal(category);
        PageMake pageMake = new PageMake(cri,total);
        model.addAttribute("pageMake",pageMake);
        if(loginMember == null) {
            return "products/products";
        }else{
            model.addAttribute("member", loginMember);
            return "products/loginProducts";
        }
    }

    @GetMapping("/products/{category}/{page}")
    public String paging(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
            Model model, @PathVariable String category, @PathVariable int page){
        Criteria cri = new Criteria();
        cri.setPageNum(page);

        List<Product> listPaging = productService.getListPaging(cri, category);// 페이징된 상품 전체 가져오기
        model.addAttribute("products",listPaging);
        int total = productService.getTotal(category);
        PageMake pageMake = new PageMake(cri,total);
        model.addAttribute("pageMake",pageMake);
        if(loginMember == null) {
            return "products/products";
        }else{
            model.addAttribute("member", loginMember);
            return "products/loginProducts";
        }
    }

    @GetMapping("/products")
    public String products(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                           Model model){
        List<Product> products = productService.products();
        model.addAttribute("products",products);
        if(loginMember == null) {
            return "products/products";
        }else{
            model.addAttribute("member", loginMember);
            return "products/loginProducts";
        }
    }

    @GetMapping("/products/find/{keyword}")
    public String findProducts(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
            @PathVariable String keyword, Model model){
        log.info("keyword={}",keyword);
        List<Product> productByKeyword = productService.findProductByKeyword(keyword);
        model.addAttribute("products",productByKeyword);
        if(loginMember == null) {
            return "products/searchProducts";
        }else{
            model.addAttribute("member", loginMember);
            return "products/loginSearchProducts";
        }
    }

    @GetMapping("/product/{productId}")
    public String detailProduct(@PathVariable int productId, Model model, HttpServletRequest request){
        Product product = productService.findProduct(productId);
        model.addAttribute("product",product);
        List<Product> products = productService.relatedProduct(product.getCategory());
        model.addAttribute("relateProduct",products);
        List<Review> reviews = productService.allReview(productId);
        model.addAttribute("reviews",reviews);
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("loginMember",loginMember);
        if(loginMember == null) {
            return "products/detailProduct";
        }else{
            model.addAttribute("member", loginMember);
            return "products/loginDetailProduct";
        }
    }

    @PostMapping("/product/{productId}")
    public String detailProductAndAddReview(@PathVariable int productId, Model model, HttpServletRequest request,
                                            @RequestBody String reviewContents){
        Product product = productService.findProduct(productId);
        model.addAttribute("product",product);
        List<Product> products = productService.relatedProduct(product.getCategory());
        model.addAttribute("relateProduct",products);
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Review review = new Review();
        review.setLoginId(loginMember.getLoginId());
        review.setProductId(productId);
        review.setContents(reviewContents.substring(1,reviewContents.length()-1));
        String time = Timestamp.valueOf(LocalDateTime.now()).toString();
        review.setCreateDate(time.substring(0,16));
        productService.addReview(review);
        if(loginMember == null) {
            return "redirect:/products/detailProduct";
        }else{
            model.addAttribute("member", loginMember);
            return "redirect:/products/loginDetailProduct";
        }
    }

    @GetMapping("/addProduct")
    public String addProductForm(@ModelAttribute Product product){
        return "products/addProduct";
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute("product") ProductSaveForm form, BindingResult bindingResult, @RequestParam String selected) throws IOException {
        if (bindingResult.hasErrors()) return "products/addProduct";
        UploadFile imageFile = fileStore.storeFile(form.getProductImage());
        Product product = new Product();
        product.setProductId(form.getProductId());
        product.setProductName(form.getProductName());
        product.setProductContents(form.getProductContents());
        product.setProductImage(imageFile);
        product.setPrice(form.getPrice());
        product.setQuantity(form.getQuantity());
        product.setCategory(selected);
        productService.addProduct(product);
        return "redirect:/products/"+selected;
    }

    // 이미지 경로 가져오기
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource getImage(@PathVariable String filename) throws MalformedURLException {
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
    public String editProduct(@PathVariable int productId, @RequestParam String selected, @ModelAttribute("product") ProductSaveForm form,
                              BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) return "products/editProduct";
        log.info("selected={}",selected);
        UploadFile imageFile = fileStore.storeFile(form.getProductImage());
        Product updateProduct = new Product();
        updateProduct.setProductId(productId);
        updateProduct.setProductName(form.getProductName());
        updateProduct.setProductContents(form.getProductContents());
        updateProduct.setPrice(form.getPrice());
        updateProduct.setQuantity(form.getQuantity());
        updateProduct.setCategory(selected);
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

    @GetMapping("/review/delete/{reviewId}")
    public String deleteReview(@PathVariable int reviewId){
        productService.deleteReview(reviewId);
        return "redirect:/product/{productId}";
    }
}
