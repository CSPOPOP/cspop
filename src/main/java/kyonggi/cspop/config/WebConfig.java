package kyonggi.cspop.config;

import kyonggi.cspop.interceptor.AdminCheckInterceptor;
import kyonggi.cspop.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 비로그인 사용자는 홈, sql, css, js, error page, 회원가입, 로그인, 비밀번호 찾기, 공지사항 페이지 제외 접근 불가
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/sql/**", "/assets/**", "/*.ico", "/error/**", "/", "/api/home",
                        "/api/signup", "/api/login", "/api/passwordReset", "/api/user/**",
                        "/api/login/auth", "/api/passwordQuestion", "/api/answerPassword",
                        "/api/editPassword/**", "/notice/find", "/notice/view/detail/**");


        // 관리자가 아닐 경우 제한될 페이지
        registry.addInterceptor(new AdminCheckInterceptor())
                .order(2)
                .addPathPatterns()
                .excludePathPatterns();
    }
}
