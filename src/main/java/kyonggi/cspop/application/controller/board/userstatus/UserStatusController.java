package kyonggi.cspop.application.controller.board.userstatus;

import kyonggi.cspop.application.SessionFactory;
import kyonggi.cspop.application.controller.board.userstatus.dto.UserDetailDto;
import kyonggi.cspop.application.controller.board.userstatus.dto.UserScheduleDto;
import kyonggi.cspop.application.controller.form.finalForm.FinalFormDto;
import kyonggi.cspop.application.controller.form.finalForm.FinalRejectionViewDto;
import kyonggi.cspop.application.controller.form.finalForm.FinalViewDto;
import kyonggi.cspop.application.controller.form.interimForm.InterimFormDto;
import kyonggi.cspop.application.controller.form.interimForm.InterimRejectionViewDto;
import kyonggi.cspop.application.controller.form.interimForm.InterimViewDto;
import kyonggi.cspop.application.controller.form.otherform.OtherFormDto;
import kyonggi.cspop.application.controller.form.otherform.OtherRejectionViewDto;
import kyonggi.cspop.application.controller.form.otherform.OtherViewDto;
import kyonggi.cspop.application.controller.form.proposalform.ProposalFormDto;
import kyonggi.cspop.application.controller.form.proposalform.ProposalRejectionViewDto;
import kyonggi.cspop.application.controller.form.proposalform.ProposalViewDto;
import kyonggi.cspop.application.controller.form.submitform.SubmitFormDto;
import kyonggi.cspop.application.controller.form.submitform.SubmitViewDto;
import kyonggi.cspop.application.util.FileStore;
import kyonggi.cspop.domain.board.excel.ExcelBoard;
import kyonggi.cspop.domain.board.excel.service.ExcelBoardService;
import kyonggi.cspop.domain.form.finalform.FinalForm;
import kyonggi.cspop.domain.form.finalform.service.FinalFormService;
import kyonggi.cspop.domain.form.interimform.InterimForm;
import kyonggi.cspop.domain.form.interimform.service.InterimFormService;
import kyonggi.cspop.domain.form.otherform.OtherForm;
import kyonggi.cspop.domain.form.otherform.service.OtherFormService;
import kyonggi.cspop.domain.form.proposalform.ProposalForm;
import kyonggi.cspop.domain.form.proposalform.service.ProposalFormService;
import kyonggi.cspop.domain.form.submitform.SubmitForm;
import kyonggi.cspop.domain.form.submitform.enums.GraduationRequirements;
import kyonggi.cspop.domain.form.submitform.service.SubmitFormService;
import kyonggi.cspop.domain.login.dto.UserSessionDto;
import kyonggi.cspop.domain.schedule.Schedules;
import kyonggi.cspop.domain.schedule.enums.Step;
import kyonggi.cspop.domain.schedule.service.ScheduleService;
import kyonggi.cspop.domain.uploadfile.FinalFormUploadFile;
import kyonggi.cspop.domain.uploadfile.InterimFormUploadFile;
import kyonggi.cspop.domain.uploadfile.OtherFormUploadFile;
import kyonggi.cspop.domain.users.Users;
import kyonggi.cspop.domain.users.service.UsersService;
import kyonggi.cspop.exception.CsPopErrorCode;
import kyonggi.cspop.exception.CsPopException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/userStatus")
@Slf4j
public class UserStatusController {

    private final UsersService usersService;
    private final ExcelBoardService excelBoardService;
    private final ScheduleService scheduleService;

    private final SubmitFormService submitFormService;
    private final ProposalFormService proposalFormService;
    private final InterimFormService interimFormService;
    private final OtherFormService otherFormService;
    private final FinalFormService finalFormService;

    private final FileStore fileStore;

    @GetMapping
    public String userStatusHome(@SessionAttribute(name = SessionFactory.CSPOP_SESSION_KEY, required = false) UserSessionDto userSessionDto, Model model) {

        Users user = usersService.findUserByStudentId(userSessionDto.getStudentId());
        Optional<ExcelBoard> excelByStudentId = excelBoardService.findExcelByStudentId(user.getStudentId());
        if (noSubmitForm(user, excelByStudentId)) {
            model.addAttribute("errorMessage", true);
            model.addAttribute("userDetail", new UserDetailDto(user.getStudentId(), user.getStudentName(), user.getDepartment()));
            return "graduation/userstatus/applyGraduation";
        }

        UserDetailDto userDetailDto = createUserDeatilDto(user, excelByStudentId);
        model.addAttribute("userDetail", userDetailDto);

        List<UserScheduleDto> userSchedules = createScheduleDto(user);
        model.addAttribute("userSchedules", userSchedules);

        model.addAttribute("finalPass", userSchedules.stream().allMatch(e -> e.getApprovalStatus().equals("승인")));

        List<String> notApprovalList = createUnApprovalList(userSchedules);
        model.addAttribute("notApprovalList", notApprovalList);

        if (!Objects.isNull(user.getSubmitForm())) {
            SubmitForm submitForm = submitFormService.findSubmitForm(user.getSubmitForm().getId());
            model.addAttribute("userSubmitFormInfo", new SubmitViewDto(submitForm));
        }
        if (!Objects.isNull(user.getProposalForm())) {
            ProposalForm proposalForm = proposalFormService.findProposalForm(user.getProposalForm().getId());

            if (proposalForm.isRejection()){
                model.addAttribute("userProposalFormRejectReason",new ProposalRejectionViewDto(proposalForm));
            }
            model.addAttribute("userProposalFormInfo", new ProposalViewDto(proposalForm));
        }
        if (!Objects.isNull(user.getInterimForm())) {
            InterimForm interimForm = interimFormService.findInterimForm(user.getInterimForm().getId());

            if (interimForm.isRejection()){
                model.addAttribute("userInterimFormRejectReason",new InterimRejectionViewDto(interimForm));
            }
            model.addAttribute("userInterimFormInfo", new InterimViewDto(interimForm));
        }
        if (!Objects.isNull(user.getOtherForm())) {
            OtherForm otherForm = otherFormService.findOtherForm(user.getOtherForm().getId());

            if (otherForm.isRejection()){
                model.addAttribute("userOtherFormRejectionReason",new OtherRejectionViewDto(otherForm));
            }
            model.addAttribute("userOtherFormInfo", new OtherViewDto(otherForm));
        }
        if (!Objects.isNull(user.getFinalForm())) {
            FinalForm finalForm = finalFormService.findFinalForm(user.getFinalForm().getId());

            if (finalForm.isRejection()){
                model.addAttribute("userFinalFormRejectionReason",new FinalRejectionViewDto(finalForm));
            }
            model.addAttribute("userFinalFormInfo", new FinalViewDto(finalForm));
        }
        return "graduation/userstatus/userGraduationStatus";
    }

    @GetMapping("/modifySubmitForm")
    public String SubmitForm(@RequestParam("submitFormId") Long submitFormId, Model model) {

        SubmitForm submitForm = submitFormService.findSubmitForm(submitFormId);
        model.addAttribute("submitForm", new SubmitViewDto(submitForm));
        return "graduation/form/modal/submitFormModal";
    }

    @GetMapping("/modifyProposalForm")
    public String ProposalForm(@RequestParam("proposalFormId") Long proposalFormId, Model model) {

        ProposalForm proposalForm = proposalFormService.findProposalForm(proposalFormId);
        model.addAttribute("proposalForm", new ProposalViewDto(proposalForm));
        return "graduation/form/modal/proposalFormModal";
    }

    @GetMapping("/modifyInterimForm")
    public String InterimForm(@RequestParam("interimFormId") Long interimFormId, Model model) {

        InterimForm interimForm = interimFormService.findInterimForm(interimFormId);
        model.addAttribute("interimForm", new InterimViewDto(interimForm));
        return "graduation/form/modal/InterimFormModal";
    }

    @GetMapping("/modifyOtherForm")
    public String OtherForm(@RequestParam("otherFormId") Long otherFormId, Model model) {

        OtherForm otherForm = otherFormService.findOtherForm(otherFormId);
        model.addAttribute("otherForm", new OtherViewDto(otherForm));
        return "graduation/form/modal/otherFormModal";
    }

    @GetMapping("/modifyFinalForm")
    public String FinalForm(@RequestParam("finalFormId") Long finalFormId, Model model) {

        FinalForm finalForm = finalFormService.findFinalForm(finalFormId);
        model.addAttribute("finalForm", new FinalViewDto(finalForm));
        return "graduation/form/modal/finalFormModal";
    }

    @PostMapping("/modifySubmitForm")
    public ResponseEntity<Void> modifySubmitForm(@RequestParam("submitFormId") Long submitFormId, @Validated @ModelAttribute SubmitFormDto submitFormDto, BindingResult result) {

        if (result.hasFieldErrors()) {
            throw new CsPopException(CsPopErrorCode.FORM_HAS_NULL_CONTENT);
        }
        submitFormService.updateUserSubmitForm(submitFormId, submitFormDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/modifyProposalForm")
    public ResponseEntity<Void> modifyProposalForm(@RequestParam("proposalFormId") Long proposalFormId, @Validated @ModelAttribute ProposalFormDto proposalFormDto, BindingResult result) {

        if (result.hasFieldErrors()) {
            throw new CsPopException(CsPopErrorCode.FORM_HAS_NULL_CONTENT);
        }
        proposalFormService.updateUserProposalForm(proposalFormId, proposalFormDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/modifyInterimForm")
    public ResponseEntity<Void> modifyInterimForm(@RequestParam("interimFormId") Long interimFormId, @Validated @ModelAttribute InterimFormDto interimFormDto, BindingResult result) throws IOException {

        if (result.hasFieldErrors() || interimFormDto.getInterimFormUploadFile().isEmpty()) {
            throw new CsPopException(CsPopErrorCode.FORM_HAS_NULL_CONTENT);
        }
        InterimFormUploadFile interimFormUploadFile = fileStore.storeInterimFile(interimFormDto.getInterimFormUploadFile());
        interimFormService.updateUserInterimForm(interimFormId, interimFormDto, interimFormUploadFile);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/modifyFinalForm")
    public ResponseEntity<Void> modifyFinalForm(@RequestParam("finalFormId") Long finalFormId, @Validated @ModelAttribute FinalFormDto finalFormDto, BindingResult result) throws IOException {

        if (result.hasFieldErrors() || finalFormDto.getFinalFormUploadFile().isEmpty()) {
            throw new CsPopException(CsPopErrorCode.FORM_HAS_NULL_CONTENT);
        }
        FinalFormUploadFile finalFormUploadFile = fileStore.storeFinalFile(finalFormDto.getFinalFormUploadFile());
        finalFormService.updateUserFinalForm(finalFormId, finalFormDto, finalFormUploadFile);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/modifyOtherForm")
    public ResponseEntity<Void> modifyOtherForm(@RequestParam("otherFormId") Long otherFormId, @Validated @ModelAttribute OtherFormDto otherFormDto, BindingResult result) throws IOException {

        if (result.hasFieldErrors() || otherFormDto.getOtherFormUploadFile().isEmpty()) {
            throw new CsPopException(CsPopErrorCode.FORM_HAS_NULL_CONTENT);
        }
        OtherFormUploadFile otherFormUploadFile = fileStore.storeOtherFile(otherFormDto.getOtherFormUploadFile());
        otherFormService.updateUserOtherForm(otherFormId, otherFormDto, otherFormUploadFile);
        return ResponseEntity.noContent().build();
    }

    /**
     * 뷰 작업자, private 접근제어 함수는 안 봐도 됩니다.
     */

    private static List<String> createUnApprovalList(List<UserScheduleDto> userSchedules) {
        List<String> notApprovalList = new ArrayList<>();
        userSchedules.stream().filter(e -> e.getApprovalStatus().equals("미승인")).forEach(e -> notApprovalList.add(e.getStep()));
        return notApprovalList;
    }

    private List<UserScheduleDto> createScheduleDto(Users user) {
        List<UserScheduleDto> userSchedules = new ArrayList<>();
        List<Schedules> scheduleList = scheduleService.findScheduleList();
        for (Schedules schedules : scheduleList) {
            if (user.getSubmitForm().getGraduationRequirements().equals(GraduationRequirements.Other_Qualifications)) {
                if (schedules.getStep().equals(Step.INTERIM_REPORT) || schedules.getStep().equals(Step.FINAL_REPORT)) {
                    continue;
                }
            }
            if (user.getSubmitForm().getGraduationRequirements().equals(GraduationRequirements.THESIS)) {
                if (schedules.getStep().equals(Step.OTHER_QUALIFICATIONS)) {
                    continue;
                }
            }

            if (schedules.getStep().equals(Step.RECEIVED)) {
                UserScheduleDto userScheduleDto = new UserScheduleDto(schedules.getStep().getStepToString(), schedules.getStartDate(), schedules.getEndDate(), Objects.isNull(user.getSubmitForm()) ? "미제출" : "완료", Objects.isNull(user.getSubmitForm()) || !user.getSubmitForm().isApproval() ? "미승인" : "승인");
                userSchedules.add(userScheduleDto);
            }

            if (schedules.getStep().equals(Step.PROPOSAL)) {
                UserScheduleDto userScheduleDto = new UserScheduleDto(schedules.getStep().getStepToString(), schedules.getStartDate(), schedules.getEndDate(), Objects.isNull(user.getProposalForm()) ? "미제출" : "완료", Objects.isNull(user.getProposalForm()) || !user.getProposalForm().isApproval() ? "미승인" : "승인");
                userSchedules.add(userScheduleDto);
            }

            if (schedules.getStep().equals(Step.INTERIM_REPORT)) {
                UserScheduleDto userScheduleDto = new UserScheduleDto(schedules.getStep().getStepToString(), schedules.getStartDate(), schedules.getEndDate(), Objects.isNull(user.getInterimForm()) ? "미제출" : "완료", Objects.isNull(user.getInterimForm()) || !user.getInterimForm().isApproval() ? "미승인" : "승인");
                userSchedules.add(userScheduleDto);
            }

            if (schedules.getStep().equals(Step.FINAL_REPORT)) {
                UserScheduleDto userScheduleDto = new UserScheduleDto(schedules.getStep().getStepToString(), schedules.getStartDate(), schedules.getEndDate(), Objects.isNull(user.getFinalForm()) ? "미제출" : "완료", Objects.isNull(user.getFinalForm()) || !user.getFinalForm().isApproval() ? "미승인" : "승인");
                userSchedules.add(userScheduleDto);
            }

            if (schedules.getStep().equals(Step.OTHER_QUALIFICATIONS)) {
                UserScheduleDto userScheduleDto = new UserScheduleDto(schedules.getStep().getStepToString(), schedules.getStartDate(), schedules.getEndDate(), Objects.isNull(user.getOtherForm()) ? "미제출" : "완료", Objects.isNull(user.getOtherForm()) || !user.getOtherForm().isApproval() ? "미승인" : "승인");
                userSchedules.add(userScheduleDto);
            }
        }
        return userSchedules;
    }

    private static UserDetailDto createUserDeatilDto(Users user, Optional<ExcelBoard> excelByStudentId) {
        String advisor = excelByStudentId.get().getProfessorName();
        UserDetailDto userDetailDto = new UserDetailDto(user.getStudentId(), user.getStudentName(), user.getDepartment(), advisor != null ? advisor : "없음", excelByStudentId.get().getCapstoneCompletion().equals("이수") ? true : false, user.getSubmitForm(), excelByStudentId.get().getGraduationDate());
        return userDetailDto;
    }

    private static boolean noSubmitForm(Users user, Optional<ExcelBoard> excelByStudentId) {
        return Objects.isNull(user.getSubmitForm()) && excelByStudentId.isEmpty();
    }
}