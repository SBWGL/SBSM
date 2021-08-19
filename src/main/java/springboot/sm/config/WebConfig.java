package springboot.sm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springboot.sm.argumentresolver.LoginMemberArgumentResolver;
import springboot.sm.interceptor.LogInterceptor;
import springboot.sm.interceptor.LoginCheckInterceptor;
import springboot.sm.interceptor.UserIntercepter;

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
                .excludePathPatterns("/", "/signUp", "/login", "/logout",
                        "/css/**", "/*.ico", "/*.js", "/images/**", "/image/**", "/js/**", "/error","/memberIdCheck","/mailCheck","/errorAdmin",
                        "products/OUTER", "products/TOP", "products/KINT", "products/SHIRT", "products/PANTS", "products/SHOES", "products/ACC", "products/find/**"
                );


        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/signUp", "/login", "/logout",
                        "/css/**", "/*.ico", "/*.js", "/images/**", "/image/**", "/js/**", "/error","/memberIdCheck","/mailCheck","/errorAdmin",
                        "/products/OUTER", "/products/TOP", "/products/KINT", "/products/SHIRT", "/products/PANTS", "/products/SHOES", "/products/ACC",
                        "/product", "/products/find/**"
                );

        registry.addInterceptor(new UserIntercepter())
                .order(3)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/signUp", "/login", "/logout",
                        "/css/**", "/*.ico", "/*.js", "/images/**", "/image/**", "/js/**", "/error","/memberIdCheck","/mailCheck","/errorAdmin",
                        "/products/OUTER", "/products/TOP", "/products/KINT", "/products/SHIRT", "/products/PANTS", "/products/SHOES", "/products/ACC",
                        "/product/*", "/basket", "/myPage", "/changePW", "/members/changePW", "/pwCheck","/basket/**", "/products/find/**"

                );



    }

}
