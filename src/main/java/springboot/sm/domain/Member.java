package springboot.sm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data /** lombok getter setter 메서드 생성 **/
@AllArgsConstructor /** 모든 변수를 파라미터로 갖는 생성자 생성 **/
@NoArgsConstructor /** 기본 생성자 생성 **/
public class Member {

    private String loginId;
    private String password;
    private String name;
    private String email;
    private String postcode;
    private String address;
    private String detailAddress;
    private String role;
}
