package kyonggi.cspop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CsPopErrorCode {

    //Users
    DUPLICATE_STUDENT_ID(HttpStatus.CONFLICT, "이미 해당 학번이 등록되어 있습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자 정보가 존재하지 않습니다."),
    WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    ANSWER_PASSWORD_NOT_FOUND(HttpStatus.NOT_FOUND, "저장되지 않은 답입니다."),

    //ExcelBoard
    INVALID_UPLOAD_FILE_EXTENSION(HttpStatus.NOT_ACCEPTABLE, "잘못된 파일 형식입니다."),
    NO_UPLOAD_FILE_EXTENSION(HttpStatus.NOT_FOUND, "엑셀파일을 업로드 해주세요."),

    //NoticeBoard
    NOTICE_BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "게시판이 이미 삭제 되었습니다.");


    private HttpStatus httpStatus;
    private String errorMessage;

    CsPopErrorCode(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
