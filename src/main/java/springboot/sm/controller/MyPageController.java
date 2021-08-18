package springboot.sm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/myPage")
    public String myPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("loginMember", loginMember);
        return "members/myPage";
    }


    @RequestMapping(value = "/changePW", method = RequestMethod.POST)
    @ResponseBody
    public String checkPw() throws Exception {
        return "members/changePW";
    }

    @GetMapping("/members/changePW")
    public String updateForm(@ModelAttribute PwUpdate pwUpdate, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("loginMember", loginMember);
        return "members/changePW";
    }

    //현재 비밀번호 확인 처리 요청
    @RequestMapping(value = "/pwCheck", method = RequestMethod.POST)
    @ResponseBody
    public String pwCheck(String password, HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (passwordEncoder.matches(password, loginMember.getPassword())) {
            log.info("success");
            return "success";
        }else{
            log.info("fail");
            return "fail";
        }
    }
    //비밀번호 변경 요청
    @PostMapping("/members/changePW")
    public String update(Model model, @ModelAttribute PwUpdate pwUpdate, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "members/loginForm";
        }
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("loginMember", loginMember);

        pwUpdate.setLoginId(loginMember.getLoginId());
        String encodedPassword = passwordEncoder.encode(pwUpdate.getChangePw());
        pwUpdate.setChangePw(encodedPassword);
        memberService.updatePw(pwUpdate);
        if (session != null) {
            session.invalidate();
        }
        return "welcome";
    }


}
