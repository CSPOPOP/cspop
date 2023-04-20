package kyonggi.cspop.application.controller.board.excel.graduate;

import kyonggi.cspop.application.controller.board.userstatus.dto.UserDetailDto;
import kyonggi.cspop.application.controller.board.userstatus.dto.UserScheduleDto;
import kyonggi.cspop.application.controller.form.FormRejectionDto;
import kyonggi.cspop.application.controller.form.finalForm.FinalViewDto;
import kyonggi.cspop.application.controller.form.interimForm.InterimViewDto;
import kyonggi.cspop.application.controller.form.otherform.OtherViewDto;
import kyonggi.cspop.application.controller.form.proposalform.ProposalViewDto;
import kyonggi.cspop.application.controller.form.submitform.SubmitViewDto;
import kyonggi.cspop.domain.board.excel.ExcelBoard;
import kyonggi.cspop.domain.board.excel.dto.ExcelBoardResponseDto;
import kyonggi.cspop.domain.board.excel.dto.ExcelBoardSubmitFormDto;
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
import kyonggi.cspop.domain.schedule.Schedules;
import kyonggi.cspop.domain.schedule.enums.Step;
import kyonggi.cspop.domain.schedule.service.ScheduleService;
import kyonggi.cspop.domain.users.Users;
import kyonggi.cspop.domain.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class GraduateCheckController {

    private final UsersService usersService;
    private final ExcelBoardService excelBoardService;
    private final ScheduleService scheduleService;
    private final SubmitFormService submitFormService;
    private final ProposalFormService proposalFormService;
    private final InterimFormService interimFormService;
    private final OtherFormService otherFormService;
    private final FinalFormService finalFormService;


    @GetMapping("api/graduation/graduate_management")
    public String graduateForm(Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> allExcelBoard = excelBoardService.findAllExcelBoard(pageable);

        int pageNumber = allExcelBoard.getPageable().getPageNumber();
        int totalPages = allExcelBoard.getTotalPages();
        int pageBlock = 10;
        int startBlockPage = ((pageNumber) / pageBlock) * pageBlock + 1;
        int endBlockPage = startBlockPage + pageBlock - 1;
        endBlockPage = Math.min(totalPages, endBlockPage);

        model.addAttribute("startBlockPage", startBlockPage);
        model.addAttribute("endBlockPage", endBlockPage);
        model.addAttribute("graduator", allExcelBoard);
        return "graduation/graduator/graduation_list";
    }

    @GetMapping("api/userStatus/approvalUser/{studentId}")
    public String userGraduationStatusForm(@PathVariable String studentId, Model model) {
        Users user = usersService.findUserByStudentId(studentId);
        Optional<ExcelBoard> excelByStudentId = excelBoardService.findExcelByStudentId(user.getStudentId());

        UserDetailDto userDetailDto = createUserDetailDto(user, excelByStudentId);
        model.addAttribute("userDetail", userDetailDto);

        List<UserScheduleDto> userSchedules = createUserScheduleDto(user);
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
            model.addAttribute("userProposalFormInfo", new ProposalViewDto(proposalForm));
        }
        if (!Objects.isNull(user.getInterimForm())) {
            InterimForm interimForm = interimFormService.findInterimForm(user.getInterimForm().getId());
            model.addAttribute("userInterimFormInfo", new InterimViewDto(interimForm));
        }
        if (!Objects.isNull(user.getOtherForm())) {
            OtherForm otherForm = otherFormService.findOtherForm(user.getOtherForm().getId());
            model.addAttribute("userOtherFormInfo", new OtherViewDto(otherForm));
        }
        if (!Objects.isNull(user.getFinalForm())) {
            FinalForm finalFormId = finalFormService.findFinalForm(user.getFinalForm().getId());
            model.addAttribute("userFinalFormInfo", new FinalViewDto(finalFormId));
        }
        return "graduation/userstatus/userGraduationStatus";
    }

    @PostMapping("api/userStatus/approvalUser/{studentId}")
    public ResponseEntity<Void> userApprovalProcess(@PathVariable String studentId, @Valid ExcelBoardSubmitFormDto excelBoardSubmitFormDto) {
        Users user = usersService.findUserByStudentId(studentId);
        updateExcelAndForms(excelBoardSubmitFormDto, user);
        return ResponseEntity.ok().build();
    }

    //반려 처리 로직(신청접수 제외) 반려 값이 false일 때 ->true값으로 변경 + 사유를 dto로 전달
    @PostMapping("api/userStatus/rejectionUser/{studentId}")
    public ResponseEntity<Void> userRejectionProcess(@PathVariable String studentId, @Valid FormRejectionDto formRejectionDto){
        Users user = usersService.findUserByStudentId(studentId);
        rejectFormApproval(formRejectionDto, user);
        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @GetMapping("api/graduation/graduate_management.download")
    public ResponseEntity<InputStreamResource> downloadExcel(HttpServletResponse response) {
        File tmpFile = getTmpFile();
        InputStream excelFile = getExcelFile(tmpFile);
        return ResponseEntity.ok().contentLength(tmpFile.length()).contentType(MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition", "attachment;filename=graduation.xlsx").body(new InputStreamResource(excelFile));
    }

    /**
     * 프론트 작업자는 이 밑으로 로직 안봐도 됩니다.
     * 위의 public 접근 제어자 메서드만 확인해 주세요.
     */

    private void updateExcelAndForms(ExcelBoardSubmitFormDto excelBoardSubmitFormDto, Users user) {
        if (!Objects.isNull(user.getSubmitForm())) {
            SubmitForm submitFormId = submitFormService.findSubmitForm(user.getSubmitForm().getId());

            if (!submitFormId.isApproval()) {
                excelBoardService.updateExcelBySubmitForm(user, excelBoardSubmitFormDto);
                submitFormService.updateUserSubmitState(submitFormId.getId());
            }
        } else if (!Objects.isNull(user.getProposalForm())) {
            ProposalForm proposalFormId = proposalFormService.findProposalForm(user.getProposalForm().getId());

            if (!proposalFormId.isApproval()) {
                excelBoardService.updateApprovalState(user);
                proposalFormService.updateUserProposalState(proposalFormId.getId());
            }
        } else if (!Objects.isNull(user.getInterimForm())) {
            InterimForm interimFormId = interimFormService.findInterimForm(user.getInterimForm().getId());
            if (!interimFormId.isApproval()) {
                excelBoardService.updateApprovalState(user);
                interimFormService.updateUserInterimState(interimFormId.getId());
            }
        } else if (!Objects.isNull(user.getFinalForm())) {
            FinalForm finalFormId = finalFormService.findFinalForm(user.getFinalForm().getId());
            if (!finalFormId.isApproval()) {
                excelBoardService.updateApprovalState(user);
                finalFormService.updateUserFinalState(finalFormId.getId());
            }
        } else if (!Objects.isNull(user.getOtherForm())) {
            OtherForm otherFormId = otherFormService.findOtherForm(user.getOtherForm().getId());
            if (!otherFormId.isApproval()) {
                excelBoardService.updateApprovalState(user);
                otherFormService.updateUserOtherState(otherFormId.getId());
            }
        }
    }

    private void rejectFormApproval(FormRejectionDto formRejectionDto, Users user) {
        if (!Objects.isNull(user.getProposalForm())) {
            ProposalForm proposalFormId = proposalFormService.findProposalForm(user.getProposalForm().getId());
            if (!proposalFormId.isRejection()) {
                proposalFormService.rejectUserProposalForm(proposalFormId.getId(), formRejectionDto);
            }
        }
        else if (!Objects.isNull(user.getInterimForm())) {
            InterimForm interimFormId = interimFormService.findInterimForm(user.getInterimForm().getId());
            if (!interimFormId.isRejection()) {
                interimFormService.rejectUserProposalForm(interimFormId.getId(), formRejectionDto);
            }
        }
        else if (!Objects.isNull(user.getFinalForm())) {
            FinalForm finalFormId = finalFormService.findFinalForm(user.getFinalForm().getId());
            if (!finalFormId.isRejection()) {
                finalFormService.rejectUserFinalForm(finalFormId.getId(), formRejectionDto);
            }
        }
        else if (!Objects.isNull(user.getOtherForm())) {
            OtherForm otherFormId = otherFormService.findOtherForm(user.getOtherForm().getId());
            if (!otherFormId.isRejection()) {
                otherFormService.rejectUserOtherForm(otherFormId.getId(), formRejectionDto);
            }
        }
    }


    private File getTmpFile() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        File tmpFile = getFile(workbook);
        OutputStream fos = new FileOutputStream(tmpFile);
        workbook.write(fos);
        return tmpFile;
    }

    private static InputStream getExcelFile(File tmpFile) throws FileNotFoundException {
        InputStream res = new FileInputStream(tmpFile) {
            @Override
            public void close() throws IOException {
                super.close();
            }
        };
        return res;
    }

    private File getFile(Workbook workbook) throws IOException {
        Sheet sheet = workbook.createSheet("졸업 대상자 조회");
        int rowNo = 0;
        CellStyle headStyle = getHeadStyle(workbook);
        rowNo = createHeader(sheet, rowNo, headStyle);
        createBody(sheet, rowNo);
        setColumnSize(sheet);
        File tmpFile = File.createTempFile("TMP~", ".xlsx");
        return tmpFile;
    }

    private static void setColumnSize(Sheet sheet) {
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 3000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 3000);
        sheet.setColumnWidth(4, 3000);
        sheet.setColumnWidth(5, 3000);
        sheet.setColumnWidth(6, 3000);
        sheet.setColumnWidth(7, 3500);
    }

    private void createBody(Sheet sheet, int rowNo) {
        List<ExcelBoard> dataList = excelBoardService.findExcelList();
        for (ExcelBoard excelBoard : dataList) {
            Row row = sheet.createRow(rowNo++);
            row.createCell(0).setCellValue(excelBoard.getStudentId());
            row.createCell(1).setCellValue(excelBoard.getStudentName());
            row.createCell(2).setCellValue(excelBoard.getProfessorName());
            row.createCell(3).setCellValue(excelBoard.getGraduationDate());
            row.createCell(4).setCellValue(excelBoard.getStep());
            row.createCell(5).setCellValue(excelBoard.getState());
            row.createCell(6).setCellValue(excelBoard.getQualifications());
            row.createCell(7).setCellValue(excelBoard.getCapstoneCompletion());
        }
    }

    private static int createHeader(Sheet sheet, int rowNo, CellStyle headStyle) {
        Row headerRow = sheet.createRow(rowNo++);
        headerRow.createCell(0).setCellValue("학번");
        headerRow.createCell(1).setCellValue("학생 이름");
        headerRow.createCell(2).setCellValue("교수 이름");
        headerRow.createCell(3).setCellValue("졸업 날짜");
        headerRow.createCell(4).setCellValue("단계");
        headerRow.createCell(5).setCellValue("상태");
        headerRow.createCell(6).setCellValue("기타 자격");
        headerRow.createCell(7).setCellValue("캡스톤 이수");

        for (int i = 0; i <= 7; i++) {
            headerRow.getCell(i).setCellStyle(headStyle);
        }
        return rowNo;
    }

    private static CellStyle getHeadStyle(Workbook workbook) {
        CellStyle headStyle = workbook.createCellStyle();
        headStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_ORANGE.getIndex());
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        font.setFontHeightInPoints((short) 13);
        headStyle.setFont(font);
        return headStyle;
    }

    private static List<String> createUnApprovalList(List<UserScheduleDto> userSchedules) {
        List<String> notApprovalList = new ArrayList<>();
        userSchedules.stream().filter(e -> e.getApprovalStatus().equals("미승인")).forEach(e -> notApprovalList.add(e.getStep()));
        return notApprovalList;
    }

    private List<UserScheduleDto> createUserScheduleDto(Users user) {
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

    private static UserDetailDto createUserDetailDto(Users user, Optional<ExcelBoard> excelByStudentId) {
        String advisor = excelByStudentId.get().getProfessorName();
        UserDetailDto userDetailDto = new UserDetailDto(user.getStudentId(), user.getStudentName(), user.getDepartment(), advisor != null ? advisor : "없음", excelByStudentId.get().getCapstoneCompletion().equals("이수") ? true : false, user.getSubmitForm(), excelByStudentId.get().getGraduationDate());
        return userDetailDto;
    }

    /**
     * 작업 후 삭제될 테스트 컨트롤러
     */

    @GetMapping("api/userStatus/approvalUser/{studentId}/update")
    public String testView(@PathVariable String studentId) {
        Users user = usersService.findUserByStudentId(studentId);
        return "graduation/form/submitApprovalForm";
    }

    @PostMapping("api/userStatus/approvalUser/{studentId}/update")
    public String test(@PathVariable String studentId, @Valid ExcelBoardSubmitFormDto excelBoardSubmitFormDto) {

        Users user = usersService.findUserByStudentId(studentId);

        if (!Objects.isNull(user.getSubmitForm())) {
            SubmitForm submitFormId = submitFormService.findSubmitForm(user.getSubmitForm().getId());

            if (!submitFormId.isApproval()) {
                excelBoardService.updateExcelBySubmitForm(user, excelBoardSubmitFormDto);
                submitFormService.updateUserSubmitState(submitFormId.getId());
            }
        }

        return "redirect:/api/userStatus/approvalUser/{studentId}";
    }

}