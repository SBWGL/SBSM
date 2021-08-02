package springboot.sm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import springboot.sm.domain.Member;
import springboot.sm.service.MemberService;


@Controller
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    MemberService memberService; /** DP_ @Autowired로 의존성 주입 즉 memberService 변수 안에 객체를 상황에 따라 변경가능 **/

    @GetMapping("/") /** DP_ url로 "/" 가 들어오면 즉 요청 받으면 resources/templates/welcome.html page 리턴 **/
    public String home() {
        return "welcome";
    }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute Member member) {

        System.out.println("LoginForm");
        return "members/LoginForm";
    }



    @GetMapping("/signUp")
    public String signUpForm(@ModelAttribute Member member) {


        return "members/SignUpForm";
    }

    @PostMapping("/signUp")
    public String memberSave(@ModelAttribute Member member, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/addMemberForm";
        }
        /** DP_ Member객체인 member로 signUpForm 이동 **/
        memberService.memberSave(member);
        return "redirect:/";
    }


}
