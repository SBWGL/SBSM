package springboot.sm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springboot.sm.domain.Member;
import springboot.sm.domain.updateform.PwUpdate;
import springboot.sm.service.MemberService;
import springboot.sm.web.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MyPageController {

    @Autowired
    MemberService memberService;

    @GetMapping("/myPage")
    public String myPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("loginMember",loginMember);
        return "members/myPage";
    }


    @RequestMapping(value="/changePW", method= RequestMethod.POST)
    @ResponseBody
    public String checkPw() throws Exception{
        return "members/changePW";
    }

    @GetMapping("/members/changePW")
    public String updateForm() {
        return "members/changePW";
    }

    @PostMapping("/members/changePW")
    public String update(Model model, @ModelAttribute PwUpdate pwUpdate, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "members/loginForm";
        }
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("loginMember",loginMember);

        return "members/changePW";
    }


}
