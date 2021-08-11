package springboot.sm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.sm.domain.productform.GetProduct;
import springboot.sm.domain.Product;
import springboot.sm.domain.UploadFile;
import springboot.sm.mapper.ProductMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    public void addProduct(Product product){
        GetProduct getProduct = inputBoardData(product);
        productMapper.addProduct(getProduct);
    }

    public void updateProduct(Product product){
        GetProduct getProduct = inputBoardData(product);
        productMapper.updateProduct(getProduct);
    }

    public GetProduct inputBoardData(Product product){
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

    public List<Product> products(){
        List<Product> convertProduct = new ArrayList<>();
        List<GetProduct> productAll = productMapper.findProductAll();
        for (GetProduct getProduct : productAll) {
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

    public UploadFile findImage(int idx){
        return productMapper.findImage(idx);
    }

}
