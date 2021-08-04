package springboot.sm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springboot.sm.domain.loginform.LoginForm;
import springboot.sm.domain.Member;
import springboot.sm.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    @Autowired
    MemberService memberService; /** DP_ @Autowired로 의존성 주입 즉 memberService 변수 안에 객체를 상황에 따라 변경가능 **/

    @GetMapping("/") /** DP_ url로 "/" 가 들어오면 즉 요청 받으면 resources/templates/welcome.html page 리턴 **/
    public String home() {
        return "welcome";
    }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginForm loginForm) {


        return "members/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginForm loginform, BindingResult bindingResult,
                      @RequestParam(defaultValue = "/") String redirectUrl,HttpServletRequest httpServletRequest){
        if (bindingResult.hasErrors()) return "members/loginForm";
        Member loginMember = memberService.login(loginform.getLoginId(), loginform.getPassword());
        log.info("loginMember={}",loginMember);
        if (loginMember == null){
            bindingResult.reject("loginFail","loginId OR password ERROR");
            return "members/loginForm";
        }
//        HttpSession session = httpServletRequest.getSession();
//        session.setAttribute(SessionConst.LOGIN_MEMBER,loginMember);
//        session.setAttribute(SessionConst.WRITER,form.getLoginId());
        return "redirect:"+redirectUrl;
    }
    /** DP_ DB 필드와 변수이름 맞추는게 안전 (오류난적이씀 ㅠㅠ) **/

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
        memberService.memberSave(member);
        return "redirect:/";
    }


}
