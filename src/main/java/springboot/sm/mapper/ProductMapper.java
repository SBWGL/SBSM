package springboot.sm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import springboot.sm.domain.productform.GetProduct;

import java.util.List;

@Mapper
@Repository
public interface ProductMapper {

    List<GetProduct> findProductAll();
    void addProduct(GetProduct getProduct);
}
