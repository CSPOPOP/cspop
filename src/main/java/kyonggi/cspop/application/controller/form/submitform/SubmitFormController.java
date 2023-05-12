package kyonggi.cspop.application.controller.form.submitform;

import kyonggi.cspop.application.SessionFactory;
import kyonggi.cspop.application.controller.form.submitform.dto.SubmitFormDto;
import kyonggi.cspop.domain.board.excel.service.ExcelBoardService;
import kyonggi.cspop.domain.form.submitform.SubmitForm;
import kyonggi.cspop.domain.form.submitform.service.SubmitFormService;
import kyonggi.cspop.domain.login.dto.UserSessionDto;
import kyonggi.cspop.domain.users.Users;
import kyonggi.cspop.domain.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class SubmitFormController {

    private final UsersService usersService;
    private final SubmitFormService submitFormService;
    private final ExcelBoardService excelBoardService;

    @PostMapping("api/submitForm")
    public String saveSubmitFormProgress(@SessionAttribute(name = SessionFactory.CSPOP_SESSION_KEY, required = false) UserSessionDto userSessionDto, @Validated @ModelAttribute SubmitFormDto submitFormDto) {

        Users user = usersService.findUserByStudentId(userSessionDto.getStudentId());
        SubmitForm submitForm = SubmitForm.createSubmitForm(submitFormDto.getStudentId(), submitFormDto.getStudentName(), submitFormDto.getDepartment(), submitFormDto.getQualification());
        Long submitFormId = submitFormService.saveSubmitForm(submitForm);
        usersService.updateUserBySubmitForm(user.getId(),submitFormId);
        excelBoardService.addExcelBySubmitForm(user, submitForm);
        return "redirect:/api/userStatus";
    }
}
