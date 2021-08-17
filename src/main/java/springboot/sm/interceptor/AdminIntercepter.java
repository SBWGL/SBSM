package springboot.sm.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;
import springboot.sm.domain.Member;
import springboot.sm.web.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class AdminIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {

        log.info("Admin 인증 체크 인터셉터 실행 {}", request.getRequestURI());

        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        log.info(loginMember.getRole());
        if (loginMember.getRole().equals("user")) {
            log.info("관리자가 아닙니다.");
            //로그인으로 redirect
            response.sendRedirect("/errorAdmin" );
            return false;
        }else{
            log.info("너 왜 관리자?");
            return true;
        }
    }
}
