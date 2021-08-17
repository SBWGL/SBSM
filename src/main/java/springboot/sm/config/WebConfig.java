package springboot.sm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springboot.sm.argumentresolver.LoginMemberArgumentResolver;
import springboot.sm.interceptor.AdminIntercepter;
import springboot.sm.interceptor.LogInterceptor;
import springboot.sm.interceptor.LoginCheckInterceptor;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())// 인터셉터 등록
                .order(1)// 호출 순서
                .addPathPatterns("/**")// 적용 url 패턴
                .excludePathPatterns("/css/**", "/*.ico", "/error");// 제외 url 패턴

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/signUp", "/login", "/logout",
                        "/css/**", "/*.ico", "/*.js", "/js/**", "/error","/memberIdCheck","/mailCheck","/errorAdmin");

        registry.addInterceptor(new AdminIntercepter())
                .order(3)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/signUp", "/login", "/logout",
                        "/css/**", "/*.ico", "/*.js", "/js/**", "/error","/memberIdCheck","/mailCheck","/errorAdmin");

    }

}
