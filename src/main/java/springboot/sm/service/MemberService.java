package springboot.sm.service;

import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springboot.sm.domain.Member;
import springboot.sm.domain.updateform.PwUpdate;
import springboot.sm.mapper.MemberMapper;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MemberService {

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * DP_ Autowired로 의존성주입 즉 객체를 생성하지 않고도 원하는 객체를 넣을 수 있음
     **/

    public void memberSave(Member member) {
        memberMapper.memberSave(member);
    }

    public void updatePw(PwUpdate pwUpdate) {
        memberMapper.updatePw(pwUpdate);
    }

    public List<Member> findAll() {
        return memberMapper.findAll();
    }

    public Optional<Member> findByLoginId(String loginId) {
        return memberMapper.findAll().stream().filter(m -> m.getLoginId().equals(loginId)).findAny();
    } /** DP_ stream=for **/

    public Member login(String loginId, String password) {
        return memberMapper.findByLoginId(loginId).filter(m -> passwordEncoder.matches(password, m.getPassword())).orElse(null);
    }

    public int idCheck(String loginId) {
        return memberMapper.idCheck(loginId);
    }
}
