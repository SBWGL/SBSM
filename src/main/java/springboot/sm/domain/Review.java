package springboot.sm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    private int reviewId;
    private String loginId;
    private int productId;
    private String contents;
    private String createDate;

}
