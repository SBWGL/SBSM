package springboot.sm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springboot.sm.domain.loginform.LoginForm;
import springboot.sm.domain.Member;
import springboot.sm.service.MemberService;
import springboot.sm.web.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * DP_ @Autowired로 의존성 주입 즉 memberService 변수 안에 객체를 상황에 따라 변경가능
     **/


    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginForm loginForm) {


        return "members/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginForm loginform, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL, HttpServletRequest httpServletRequest) {
        /** DP_ HttpServletResponse는 쿠키 할 때 필요 **/
        if (bindingResult.hasErrors()) {
            return "members/loginForm";
        }
        Optional<Member> byLoginId = memberService.findByLoginId(loginform.getLoginId());


        if(passwordEncoder.matches(loginform.getPassword(), byLoginId.get().getPassword())) {
            log.info("비밀번호일치");
        }

        Member loginMember = memberService.login(loginform.getLoginId(), loginform.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호를 잘못 입력했습니다.");
            return "members/loginForm";
        } /** DP_ 아이디, 비밀번호 오류일 때 예외상황 **/


        //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = httpServletRequest.getSession(true);
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
//        session.setAttribute(SessionConst.WRITER,form.getLoginId());
        return "redirect:" + redirectURL;
    }

    /**
     * DP_ DB 필드와 변수이름 맞추는게 안전 (오류난적이씀 ㅠㅠ)
     **/

    @GetMapping("/signUp")
    public String signUpForm(@ModelAttribute Member member) {

        return "members/signUpForm";
    }

    @PostMapping("/signUp")
    public String memberSave(@ModelAttribute Member member, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/signUpForm";
        }
        /** DP_ Member객체인 member로 signUpForm 이동 **/
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        memberService.memberSave(member);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        log.info("실행");
        HttpSession session = request.getSession(false);
        log.info("하이 : session " + session);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/memberIdCheck", method = RequestMethod.POST)
    @ResponseBody
    public String memberIdChk(String memberId) throws Exception{
        int result = memberService.idCheck(memberId);
        if(result != 0) {
            return "fail";	// 중복 아이디가 존재
        } else {
            return "success";	// 중복 아이디 x
        }
    }


}
