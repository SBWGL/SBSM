package springboot.sm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import springboot.sm.domain.Member;
import springboot.sm.domain.updateform.PwUpdate;

import java.util.List;
import java.util.Optional;

@Repository
@Mapper /** DP_ Repository와 Mapper의 뜻 ? **/
public interface MemberMapper {

    void memberSave(Member member);
    void updatePw(PwUpdate pwUpdate);

    /**
     * Mapper랑 연결 즉 우리가 memberSave를 만들어줌
     **/
    List<Member> findAll();
    Optional<Member> findByLoginId(String loginId);
    Member login(String loginId, String password);
    int idCheck(String loginId);

    /** public 생략 ? 대표 **/
}
