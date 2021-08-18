package springboot.sm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import springboot.sm.domain.Review;
import springboot.sm.domain.common.Criteria;
import springboot.sm.domain.common.UploadFile;
import springboot.sm.domain.productform.GetProduct;

import java.util.List;

@Mapper
@Repository
public interface ProductMapper {

    List<GetProduct> findProductAll();
    void addProduct(GetProduct getProduct);
    GetProduct findProduct(int productId);
    UploadFile findImage(int idx);
    void updateProduct(GetProduct product);
    List<GetProduct> getListPaging(@Param("cri") Criteria cri,
                                   @Param("category") String category);
    int getTotal(String category);
    List<GetProduct> findProductByKeyword(String keyword);
    List<GetProduct> relatedProduct(String category);
    void addReview(Review review);
    List<Review> allReview(int productId);
    void deleteReview(int reviewId);
}
