package kyonggi.cspop.interceptor;

import kyonggi.cspop.application.SessionFactory;
import kyonggi.cspop.domain.login.dto.UserSessionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class AdminCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        log.info("관리자 인증 체크 인터셉터 실행 = {}", requestURI);
        HttpSession session = request.getSession(false);

        log.info("세션 명={}", session.getAttribute(SessionFactory.CSPOP_SESSION_KEY));

        boolean isAdmin1 = session.getAttribute(SessionFactory.CSPOP_SESSION_KEY).equals(new UserSessionDto("admin1", "관리자1", "관리자"));
        boolean isAdmin2 = session.getAttribute(SessionFactory.CSPOP_SESSION_KEY).equals(new UserSessionDto("admin2", "관리자2", "관리자"));
        boolean isAdmin3 = session.getAttribute(SessionFactory.CSPOP_SESSION_KEY).equals(new UserSessionDto("admin3", "관리자3", "관리자"));
        boolean isAdmin4 = session.getAttribute(SessionFactory.CSPOP_SESSION_KEY).equals(new UserSessionDto("admin4", "관리자4", "관리자"));
        boolean isAdmin5 = session.getAttribute(SessionFactory.CSPOP_SESSION_KEY).equals(new UserSessionDto("admin5", "관리자5", "관리자"));


        if (!isAdmin1 && !isAdmin2 && !isAdmin3 && !isAdmin4 && !isAdmin5) {
            log.info("일반 사용자 요청");
            response.sendRedirect("/api/home?param=user");
            return false;
        }

        return true;
    }
}
