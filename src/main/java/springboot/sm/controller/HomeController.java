package springboot.sm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import springboot.sm.domain.Member;
import springboot.sm.service.MemberService;
import springboot.sm.web.SessionConst;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    @Autowired
    MemberService memberService;

//    @GetMapping("/") /** DP_ url로 "/" 가 들어오면 즉 요청 받으면 resources/templates/welcome.html page 리턴 **/
//    public String home() {
//        return "welcome";
//    }

    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                    Member loginMember,Model model) {

        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "welcome";
        }
        else if (loginMember.getRole().equals("admin")){
            return "adminWelcome";
        }
        else {
            //세션이 유지되면 로그인으로 이동
            model.addAttribute("member", loginMember);
            return "loginWelcome";
        }
    }

    @GetMapping("/errorAdmin")
    public String errorAdmin() {

        return "/errorAdmin";
    }


}


