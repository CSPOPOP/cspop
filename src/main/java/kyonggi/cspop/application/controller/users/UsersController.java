package kyonggi.cspop.application.controller.users;

import kyonggi.cspop.application.controller.users.dto.UserPasswordRequestDto;
import kyonggi.cspop.application.controller.users.dto.UsersDto;
import kyonggi.cspop.domain.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UsersController {

    private final UsersService usersService;

    @GetMapping("/signup")
    public String signup() {
        return "account/signup";
    }

    @GetMapping("/passwordReset")
    public String resetPassword(){
        return "account/passwordReset";
    }

    @ResponseBody
    @PostMapping("/user")
    public String signUp(@Validated @RequestBody UsersDto usersDto) {
        usersService.saveUser(usersDto.toEntity());
        return "/api/home";
    }

    @ResponseBody
    @PostMapping("/user/duplicate-check")
    public ResponseEntity<Void> duplicateCheckInSignUp(@NotBlank @RequestBody Map<String, String> studentId) {
        usersService.checkDuplicateStudentNumber(studentId.get("studentId"));
        return ResponseEntity.noContent().build();
    }

    @ResponseBody
    @PostMapping("/passwordQuestion")
    public ResponseEntity<Void> checkUserId(@NotBlank @RequestBody Map<String, String> studentId) {

        usersService.checkExistStudentNumber(studentId.get("studentId"));
        return ResponseEntity.noContent().build();
    }

    @ResponseBody
    @PostMapping("/answerPassword")
    public ResponseEntity<Void> checkAnswerPw(@NotBlank @RequestBody Map<String, String> answerPw) {

        usersService.checkExistPasswordAnswer(answerPw.get("answerPw"));
        return ResponseEntity.noContent().build();
    }

    @ResponseBody
    @PostMapping("/editPassword/{studentId}")
    public String updatePassword(@PathVariable String studentId, @NotBlank @RequestBody UserPasswordRequestDto userPasswordRequestDto) {

        usersService.updatePassword(studentId, userPasswordRequestDto);
        return "/api/home";
    }
}