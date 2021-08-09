package springboot.sm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import springboot.sm.domain.GetProduct;
import springboot.sm.domain.Product;

import java.util.List;

@Mapper
@Repository
public interface ProductMapper {

    List<Product> findProductAll();
    void addProduct(GetProduct getProduct);
}
