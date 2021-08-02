package springboot.sm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.sm.domain.LoginForm;
import springboot.sm.domain.Member;
import springboot.sm.mapper.MemberMapper;

@Service
public class MemberService {

    @Autowired
    MemberMapper memberMapper;

    /**
     * Autowired로 의존성주입 즉 객체를 생성하지 않고도 원하는 객체를 넣을 수 있음
     **/

    public void memberSave(Member member) {
        memberMapper.memberSave(member);
    }

    public LoginForm findById(LoginForm loginForm) {
       return memberMapper.findById(loginForm);
    }
}
