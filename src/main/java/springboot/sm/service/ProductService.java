package springboot.sm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.sm.domain.common.Criteria;
import springboot.sm.domain.productform.GetProduct;
import springboot.sm.domain.Product;
import springboot.sm.domain.common.UploadFile;
import springboot.sm.mapper.ProductMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

//    상품 추가하기
    public void addProduct(Product product){
        GetProduct getProduct = inputProductData(product);
        productMapper.addProduct(getProduct);
    }
//    상품 업데이트
    public void updateProduct(Product product){
        GetProduct getProduct = inputProductData(product);
        productMapper.updateProduct(getProduct);
    }

//    추가히기 & 업데이트 리팩토링
    public GetProduct inputProductData(Product product){
        GetProduct getProduct = new GetProduct();
        getProduct.setProductId(product.getProductId());
        getProduct.setUploadImageName(product.getProductImage().getUploadImageName());
        getProduct.setStoreImageName(product.getProductImage().getStoreImageName());
        getProduct.setProductName(product.getProductName());
        getProduct.setProductContents(product.getProductContents());
        getProduct.setPrice(product.getPrice());
        getProduct.setQuantity(product.getQuantity());
        return getProduct;
    }
//    상품 전체가져오기
    public List<Product> products(){
        List<GetProduct> productAll = productMapper.findProductAll();
        return inputProductListData(productAll);
    }
//    페이징
    public List<Product> getListPaging(Criteria cri){
        List<GetProduct> listPaging = productMapper.getListPaging(cri);
        return inputProductListData(listPaging);
    }

//    상품 전체가져오기 & 페이징 리팩토링
    public List<Product> inputProductListData(List<GetProduct> productList){
        List<Product> convertProduct = new ArrayList<>();
        for (GetProduct getProduct : productList) {
            Product product = new Product();
            product.setProductId(getProduct.getProductId());
            product.setProductImage(new UploadFile(getProduct.getUploadImageName(), getProduct.getStoreImageName()));
            product.setProductName(getProduct.getProductName());
            product.setPrice(getProduct.getPrice());
            product.setQuantity(getProduct.getQuantity());
            product.setProductContents(product.getProductContents());
            convertProduct.add(product);
        }
        return convertProduct;
    }

//    상품 상세보기
    public Product findProduct(int productId){
        GetProduct product = productMapper.findProduct(productId);
        Product findProduct = new Product();
        findProduct.setProductId(product.getProductId());
        findProduct.setProductName(product.getProductName());
        findProduct.setProductContents(product.getProductContents());
        findProduct.setPrice(product.getPrice());
        findProduct.setQuantity(product.getQuantity());
        findProduct.setProductImage(new UploadFile(product.getUploadImageName(),product.getStoreImageName()));
        return findProduct;
    }

//    이미지 찾기
    public UploadFile findImage(int idx){
        return productMapper.findImage(idx);
    }

//    게시물 개수
    public int getTotal(){
        return productMapper.getTotal();
    }

}
