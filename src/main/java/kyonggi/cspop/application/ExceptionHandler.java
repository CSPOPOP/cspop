package kyonggi.cspop.application;

import kyonggi.cspop.application.controller.board.certification.EngineeringCertificationController;
import kyonggi.cspop.application.controller.board.graduate.GraduateController;
import kyonggi.cspop.application.controller.board.notice.NoticeBoardController;
import kyonggi.cspop.application.controller.board.userstatus.UserStatusController;
import kyonggi.cspop.application.controller.chatbot.ChatBotController;
import kyonggi.cspop.application.controller.users.UsersController;
import kyonggi.cspop.exception.CsPopException;
import kyonggi.cspop.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {
        UsersController.class,
        GraduateController.class,
        EngineeringCertificationController.class,
        NoticeBoardController.class,
        UserStatusController.class,
        ChatBotController.class
})
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(CsPopException ex) {

        return ResponseEntity
                .status(ex.getCsPopErrorCode().getHttpStatus())
                .body(new ErrorResponse(ex.getCsPopErrorCode()));
    }
}
