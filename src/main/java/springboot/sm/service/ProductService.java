package springboot.sm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.sm.domain.GetProduct;
import springboot.sm.domain.Product;
import springboot.sm.mapper.ProductMapper;

@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    void addProduct(Product product){
        GetProduct getProduct = new GetProduct();
        getProduct.setProductId(product.getProductId());
        getProduct.setUploadImageName(product.getProductImage().getUploadFileName());
        getProduct.setStoreImageName(product.getProductImage().getStoreFileName());
        getProduct.setProductName(product.getProductName());
        getProduct.setProductContents(product.getProductContents());
        getProduct.setSize(product.getSize());
        getProduct.setColor(product.getColor());
        getProduct.setPrice(product.getPrice());
        getProduct.setQuantity(product.getQuantity());
        productMapper.addProduct(getProduct);
    }
}
