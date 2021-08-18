package springboot.sm.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import springboot.sm.domain.Member;
import springboot.sm.web.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserIntercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember.getRole().equals("user")) {
            return true;
        }else{
            return false;
        }

    }
}
