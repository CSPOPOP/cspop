package kyonggi.cspop.application.controller.form.proposalform;


import kyonggi.cspop.application.SessionFactory;
import kyonggi.cspop.application.controller.board.userstatus.dto.UserDetailDto;
import kyonggi.cspop.domain.board.excel.ExcelBoard;
import kyonggi.cspop.domain.board.excel.service.ExcelBoardService;
import kyonggi.cspop.domain.form.proposalform.ProposalForm;
import kyonggi.cspop.domain.form.proposalform.service.ProposalFormService;
import kyonggi.cspop.domain.login.dto.UserSessionDto;
import kyonggi.cspop.domain.users.Users;
import kyonggi.cspop.domain.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProposalFormController {

    private final UsersService usersService;
    private final ProposalFormService proposalFormService;
    private final ExcelBoardService excelBoardService;

    @GetMapping("api/proposalForm")
    public String saveProposalForm(@SessionAttribute(name = SessionFactory.CSPOP_SESSION_KEY, required = false) UserSessionDto userSessionDto, Model model) {
        Users user = usersService.findUserByStudentId(userSessionDto.getStudentId());
        Optional<ExcelBoard> excelByStudentId = excelBoardService.findExcelByStudentId(user.getStudentId());
        System.out.println("excelByStudentId.get().getCapstoneCompletion() = " + excelByStudentId.get().getCapstoneCompletion());
        String advisor = excelByStudentId.get().getProfessorName();
        UserDetailDto userDetailDto = new UserDetailDto(
                user.getStudentId(),
                excelByStudentId.get().getGraduationDate(),
                user.getStudentName(),
                user.getDepartment(),
                excelByStudentId.get().getProfessorName(),
                user.getSubmitForm(),
                excelByStudentId.get().getCapstoneCompletion().equals("이수") ? true : false);
        model.addAttribute("userDetail", userDetailDto);
        return "graduation/form/proposalForm";
    }

    @PostMapping("api/proposalForm")
    public String saveProposalFormProgress(@SessionAttribute(name = SessionFactory.CSPOP_SESSION_KEY, required = false) UserSessionDto userSessionDto, @Validated  @ModelAttribute ProposalFormDto proposalFormDto) {

        Users user = usersService.findUserByStudentId(userSessionDto.getStudentId());
        Optional<ExcelBoard> excelByStudentId = excelBoardService.findExcelByStudentId(user.getStudentId());
        String advisor = excelByStudentId.get().getProfessorName();

        //제안서 폼 등록
        ProposalForm proposalForm = ProposalForm.createProposalForm(user.getStudentId(), user.getStudentName(), user.getDepartment(), excelByStudentId.get().getGraduationDate(), excelByStudentId.get().getProfessorName(), excelByStudentId.get().getQualifications(), proposalFormDto.getTitle(), proposalFormDto.getDivision(), proposalFormDto.getKeyword(), proposalFormDto.getText());
        Long proposalFormId = proposalFormService.saveProposalForm(proposalForm);

        //유저 테이블 수정
        usersService.updateUserByProposalForm(user.getId(), proposalFormId);

        //엑셀보드 업데이트
        excelBoardService.updateExcelByProposalForm(user);

        log.info("폼 = {}", proposalFormDto);
        //신청 폼 저장 -> 액셀 업데이트 -> 졸업 진행 상황 테이블 업데이트 -> 신청자 리스트 업데이트
        return "redirect:/api/userStatus";
    }
}
