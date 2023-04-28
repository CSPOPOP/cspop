package kyonggi.cspop.interceptor;

import kyonggi.cspop.application.SessionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        log.info("인증 체크 인터셉터 실행 = {}", requestURI);
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(SessionFactory.CSPOP_SESSION_KEY) == null) {
            log.info("미인증 사용자 요청");
            response.sendRedirect("/api/home");
            return false;
        }

        return true;
    }
}
