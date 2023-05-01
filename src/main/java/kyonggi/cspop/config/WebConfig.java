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

        // 비로그인 사용자는 sql, css, js, error page, 회원가입, 로그인, 비밀번호 찾기, 검색 기능만 사용 가능
        // 홈, 공지사항 페이지 제외 접근 불가
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/sql/**", "/assets/**", "/*.ico", "/error/**", "/", "/api/home",
                        "/api/signup", "/api/login", "/api/passwordReset", "/api/user/**",
                        "/api/login/auth", "/api/passwordQuestion", "/api/answerPassword", "/api/logout",
                        "/api/editPassword/**", "/notice/find", "/api/notice/search", "/notice/view/detail/**");


        //이 외 페이지는 관리자 권한일 경우에만 접근 가능
        registry.addInterceptor(new AdminCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/sql/**", "/assets/**", "/*.ico", "/error/**", "/", "/api/home",
                        "/api/signup", "/api/login", "/api/passwordReset", "/api/user/**",
                        "/api/login/auth", "/api/passwordQuestion", "/api/answerPassword", "/api/logout",
                        "/api/editPassword/**", "/notice/find", "/api/notice/search", "/notice/view/detail/**",
                        "/api/graduation/guide", "/api/graduation/schedule", "/api/userStatus",
                        "/api/submitForm", "/api/proposalForm", "/api/interimForm", "/api/finalForm",
                        "/api/otherForm", "/api/userStatus/modifySubmitForm",
                        "/api/userStatus/modifyProposalForm", "/api/userStatus/modifyInterimForm",
                        "/api/userStatus/modifyFinalForm", "/api/userStatus/modifyOtherForm");

    }
}
