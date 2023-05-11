package kyonggi.cspop.application.controller.form.proposalform;


import kyonggi.cspop.application.SessionFactory;
import kyonggi.cspop.application.controller.board.userstatus.dto.UserDetailDto;
import kyonggi.cspop.application.controller.form.proposalform.dto.ProposalFormDto;
import kyonggi.cspop.domain.board.excel.ExcelBoard;
import kyonggi.cspop.domain.board.excel.service.ExcelBoardService;
import kyonggi.cspop.domain.form.proposalform.ProposalForm;
import kyonggi.cspop.domain.form.proposalform.service.ProposalFormService;
import kyonggi.cspop.domain.login.dto.UserSessionDto;
import kyonggi.cspop.domain.users.Users;
import kyonggi.cspop.domain.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProposalFormController {

    private final UsersService usersService;
    private final ProposalFormService proposalFormService;
    private final ExcelBoardService excelBoardService;

    @GetMapping("api/proposalForm")
    public String saveProposalForm(@SessionAttribute(name = SessionFactory.CSPOP_SESSION_KEY, required = false) UserSessionDto userSessionDto, Model model) {
        Users user = usersService.findUserByStudentId(userSessionDto.getStudentId());
        Optional<ExcelBoard> excelByStudentId = excelBoardService.findExcelByStudentId(user.getStudentId());
        UserDetailDto userDetailDto = getUserDetailDto(user, excelByStudentId);

        model.addAttribute("userDetail", userDetailDto);
        return "graduation/form/proposalForm";
    }

    @PostMapping("api/proposalForm")
    public String saveProposalFormProgress(@SessionAttribute(name = SessionFactory.CSPOP_SESSION_KEY, required = false) UserSessionDto userSessionDto, @Validated @ModelAttribute ProposalFormDto proposalFormDto, BindingResult bindingResult, Model model) {

        Users user = usersService.findUserByStudentId(userSessionDto.getStudentId());
        Optional<ExcelBoard> excelByStudentId = excelBoardService.findExcelByStudentId(user.getStudentId());

        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("errorMessage", true);

            UserDetailDto userDetailDto = getUserDetailDto(user, excelByStudentId);
            model.addAttribute("userDetail", userDetailDto);
            return "graduation/form/proposalForm";
        }
        ProposalForm proposalForm = ProposalForm.createProposalForm(user.getStudentId(), user.getStudentName(), user.getDepartment(), excelByStudentId.get().getGraduationDate(), excelByStudentId.get().getProfessorName(), excelByStudentId.get().getQualifications(), proposalFormDto.getTitle(), proposalFormDto.getDivision(), proposalFormDto.getKeyword(), proposalFormDto.getText());
        Long proposalFormId = proposalFormService.saveProposalForm(proposalForm);
        usersService.updateUserByProposalForm(user.getId(), proposalFormId);
        excelBoardService.updateExcelByProposalForm(user);
        return "redirect:/api/userStatus";
    }

    private static UserDetailDto getUserDetailDto(Users user, Optional<ExcelBoard> excelByStudentId) {
        return new UserDetailDto(
                user.getStudentId(),
                excelByStudentId.get().getGraduationDate(),
                user.getStudentName(),
                user.getDepartment(),
                excelByStudentId.get().getProfessorName(),
                user.getSubmitForm(),
                excelByStudentId.get().getCapstoneCompletion().equals("이수"));
    }
}
