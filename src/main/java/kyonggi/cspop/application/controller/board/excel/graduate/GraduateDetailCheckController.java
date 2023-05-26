package kyonggi.cspop.application.controller.board.excel.graduate;

import kyonggi.cspop.application.util.ExcelStore;
import kyonggi.cspop.application.util.PageStore;
import kyonggi.cspop.domain.board.excel.dto.ExcelBoardResponseDto;
import kyonggi.cspop.domain.board.excel.service.ExcelBoardService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.InputStream;


@Controller
@RequiredArgsConstructor
@RequestMapping("api/graduation")
public class GraduateDetailCheckController {

    private final ExcelBoardService excelBoardService;
    private final PageStore pageStore;
    private final ExcelStore excelStore;


    @GetMapping("/graduate_submitForm")
    public String graduateSubmitForm(Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> graduator = excelBoardService.findAllStep(pageable, "신청접수");
        int[] startAndEndBlockPage = pageStore.getStartAndEndBlockPage(graduator.getPageable().getPageNumber(), graduator.getTotalPages());
        putPagingInf(model, graduator, startAndEndBlockPage);
        return "graduation/graduator/step/submitFormStep_list";
    }

    @SneakyThrows
    @GetMapping("/graduate_submitForm.downloadSubmitFormStep")
    public ResponseEntity<InputStreamResource> downloadGraduationExcelFileOfSubmitFormStep() {
        File tmpFile = excelStore.getTmpSubmitFormFile();
        InputStream excelFile = excelStore.getExcelFile(tmpFile);
        return ResponseEntity.ok().contentLength(tmpFile.length()).contentType(MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition", "attachment;filename=application.xlsx").body(new InputStreamResource(excelFile));
    }

    @GetMapping("/graduate_proposalForm")
    public String graduateProposalForm(Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> graduator = excelBoardService.findAllStep(pageable,"제안서");
        int[] startAndEndBlockPage = pageStore.getStartAndEndBlockPage(graduator.getPageable().getPageNumber(), graduator.getTotalPages());
        putPagingInf(model, graduator, startAndEndBlockPage);
        return "graduation/graduator/step/proposalFormStep_list";
    }

    @SneakyThrows
    @GetMapping("/graduate_proposalForm.downloadProposalFormStep")
    public ResponseEntity<InputStreamResource> downloadGraduationExcelFileOfProposalFormStep() {
        File tmpFile = excelStore.getTmpProposalFormFile();
        InputStream excelFile = excelStore.getExcelFile(tmpFile);
        return ResponseEntity.ok().contentLength(tmpFile.length()).contentType(MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition", "attachment;filename=proposal.xlsx").body(new InputStreamResource(excelFile));
    }

    @GetMapping("/graduate_interimForm")
    public String graduateInterimForm(Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> graduator = excelBoardService.findAllStep(pageable,"중간보고서");
        int[] startAndEndBlockPage = pageStore.getStartAndEndBlockPage(graduator.getPageable().getPageNumber(), graduator.getTotalPages());
        putPagingInf(model, graduator, startAndEndBlockPage);
        return "graduation/graduator/step/interimFormStep_list";
    }

    @SneakyThrows
    @GetMapping("/graduate_interimForm.downloadInterimFormStep")
    public ResponseEntity<InputStreamResource> downloadGraduationExcelFileOfInterimFormStep() {
        File tmpFile = excelStore.getTmpInterimFormFile();
        InputStream excelFile = excelStore.getExcelFile(tmpFile);
        return ResponseEntity.ok().contentLength(tmpFile.length()).contentType(MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition", "attachment;filename=interimForm.xlsx").body(new InputStreamResource(excelFile));
    }

    @GetMapping("/graduate_finalForm")
    public String graduateFinalForm(Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> graduator = excelBoardService.findAllStep(pageable,"최종보고서");
        int[] startAndEndBlockPage = pageStore.getStartAndEndBlockPage(graduator.getPageable().getPageNumber(), graduator.getTotalPages());
        putPagingInf(model, graduator, startAndEndBlockPage);
        return "graduation/graduator/step/finalFormStep_list";
    }

    @SneakyThrows
    @GetMapping("/graduate_finalForm.downloadFinalFormStep")
    public ResponseEntity<InputStreamResource> downloadGraduationExcelFileOfFinalFormStep() {
        File tmpFile = excelStore.getTmpFinalFormFile();
        InputStream excelFile = excelStore.getExcelFile(tmpFile);
        return ResponseEntity.ok().contentLength(tmpFile.length()).contentType(MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition", "attachment;filename=finalForm.xlsx").body(new InputStreamResource(excelFile));
    }

    @GetMapping("/graduate_otherForm")
    public String graduateOtherForm(Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> graduator = excelBoardService.findAllStep(pageable,"기타자격");
        int[] startAndEndBlockPage = pageStore.getStartAndEndBlockPage(graduator.getPageable().getPageNumber(), graduator.getTotalPages());
        putPagingInf(model, graduator, startAndEndBlockPage);
        return "graduation/graduator/step/otherFormStep_list";
    }

    @SneakyThrows
    @GetMapping("/graduate_otherForm.downloadOtherQualificationStep")
    public ResponseEntity<InputStreamResource> downloadGraduationExcelFileOfOtherQualificationStep() {
        File tmpFile = excelStore.getTmpOtherFormFile();
        InputStream excelFile = excelStore.getExcelFile(tmpFile);
        return ResponseEntity.ok().contentLength(tmpFile.length()).contentType(MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition", "attachment;filename=otherQualification.xlsx").body(new InputStreamResource(excelFile));
    }

    @GetMapping("/graduate_finalPass")
    public String graduateFinalPass(Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> graduator = excelBoardService.findAllStep(pageable,"최종통과");
        int[] startAndEndBlockPage = pageStore.getStartAndEndBlockPage(graduator.getPageable().getPageNumber(), graduator.getTotalPages());
        putPagingInf(model, graduator, startAndEndBlockPage);
        return "graduation/graduator/step/finalPassStep_list";
    }

    @SneakyThrows
    @GetMapping("/graduate_finalPass.downloadFinalPassStep")
    public ResponseEntity<InputStreamResource> downloadGraduationExcelFileOfFinalPassStep() {
        File tmpFile = excelStore.getTmpFinalPassFile();
        InputStream excelFile = excelStore.getExcelFile(tmpFile);
        return ResponseEntity.ok().contentLength(tmpFile.length()).contentType(MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition", "attachment;filename=finalPass.xlsx").body(new InputStreamResource(excelFile));
    }

    private static void putPagingInf(Model model, Page<ExcelBoardResponseDto> graduator, int[] startAndEndBlockPage) {
        model.addAttribute("startBlockPage", startAndEndBlockPage[0]);
        model.addAttribute("endBlockPage", startAndEndBlockPage[1]);
        model.addAttribute("graduator", graduator);
    }
}
