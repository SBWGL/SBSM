package springboot.sm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import springboot.sm.domain.LoginForm;
import springboot.sm.domain.Member;

@Repository
@Mapper /** DP_ Repository와 Mapper의 뜻 ? **/
public interface MemberMapper {

    void memberSave(Member member); /** Mapper랑 연결 즉 우리가 memberSave를 만들어줌 **/

    LoginForm findById(LoginForm loginForm);

}
