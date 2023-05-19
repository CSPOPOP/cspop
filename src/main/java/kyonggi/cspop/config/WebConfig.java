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
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/sql/**", "/assets/**", "/*.ico", "/error/**", "/", "/api/home",
                        "/api/signup", "/api/login", "/api/passwordReset", "/api/user/**",
                        "/api/login/auth", "/api/passwordQuestion", "/api/answerPassword", "/api/logout",
                        "/api/editPassword/**", "/notice/find", "/api/notice/search", "/notice/view/detail/**");

        registry.addInterceptor(new AdminCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/sql/**", "/assets/**", "/*.ico", "/error/**", "/", "/api/home",
                        "/api/signup", "/api/login", "/api/passwordReset", "/api/user/**",
                        "/api/login/auth", "/api/passwordQuestion", "/api/answerPassword", "/api/logout",
                        "/api/editPassword/**", "/notice/find", "/api/notice/search", "/notice/view/detail/**",
                        "/api/graduation/guide", "/api/graduation/schedule", "/api/userStatus",
                        "/api/submitForm", "/api/proposalForm", "/api/interimForm", "/api/finalForm",
                        "/api/otherForm", "/api/userStatus/modifySubmitForm", "/api/attach/**",
                        "/api/userStatus/modifyProposalForm", "/api/userStatus/modifyInterimForm", "/attach/**",
                        "/api/userStatus/modifyFinalForm", "/api/userStatus/modifyOtherForm", "/api/chatBot", "/api/chatBotPage");

    }
}
