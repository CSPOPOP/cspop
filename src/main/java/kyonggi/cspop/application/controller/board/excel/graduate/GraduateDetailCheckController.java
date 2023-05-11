package kyonggi.cspop.application.controller.board.excel.graduate;

import kyonggi.cspop.application.util.PageStore;
import kyonggi.cspop.domain.board.excel.dto.ExcelBoardResponseDto;
import kyonggi.cspop.domain.board.excel.service.ExcelBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("api/graduation")
public class GraduateDetailCheckController {

    private final ExcelBoardService excelBoardService;
    private final PageStore pageStore;


    @GetMapping("/graduate_submitForm")
    public String graduateSubmitForm(Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> graduator = excelBoardService.findAllStep(pageable, "신청접수");
        int[] startAndEndBlockPage = pageStore.getStartAndEndBlockPage(graduator.getPageable().getPageNumber(), graduator.getTotalPages());
        putPagingInf(model, graduator, startAndEndBlockPage);
        return "graduation/graduator/step/submitFormStep_list";
    }

    @GetMapping("/graduate_proposalForm")
    public String graduateProposalForm(Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> graduator = excelBoardService.findAllStep(pageable,"제안서");
        int[] startAndEndBlockPage = pageStore.getStartAndEndBlockPage(graduator.getPageable().getPageNumber(), graduator.getTotalPages());
        putPagingInf(model, graduator, startAndEndBlockPage);
        return "graduation/graduator/step/proposalFormStep_list";
    }

    @GetMapping("/graduate_interimForm")
    public String graduateInterimForm(Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> graduator = excelBoardService.findAllStep(pageable,"중간보고서");
        int[] startAndEndBlockPage = pageStore.getStartAndEndBlockPage(graduator.getPageable().getPageNumber(), graduator.getTotalPages());
        putPagingInf(model, graduator, startAndEndBlockPage);
        return "graduation/graduator/step/interimFormStep_list";
    }

    @GetMapping("/graduate_finalForm")
    public String graduateFinalForm(Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> graduator = excelBoardService.findAllStep(pageable,"최종보고서");
        int[] startAndEndBlockPage = pageStore.getStartAndEndBlockPage(graduator.getPageable().getPageNumber(), graduator.getTotalPages());
        putPagingInf(model, graduator, startAndEndBlockPage);
        return "graduation/graduator/step/finalFormStep_list";
    }

    @GetMapping("/graduate_otherForm")
    public String graduateOtherForm(Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> graduator = excelBoardService.findAllStep(pageable,"기타자격");
        int[] startAndEndBlockPage = pageStore.getStartAndEndBlockPage(graduator.getPageable().getPageNumber(), graduator.getTotalPages());
        putPagingInf(model, graduator, startAndEndBlockPage);
        return "graduation/graduator/step/otherFormStep_list";
    }

    @GetMapping("/graduate_finalPass")
    public String graduateFinalPass(Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> graduator = excelBoardService.findAllStep(pageable,"최종통과");
        int[] startAndEndBlockPage = pageStore.getStartAndEndBlockPage(graduator.getPageable().getPageNumber(), graduator.getTotalPages());
        putPagingInf(model, graduator, startAndEndBlockPage);
        return "graduation/graduator/step/finalPassStep_list";
    }

    private static void putPagingInf(Model model, Page<ExcelBoardResponseDto> graduator, int[] startAndEndBlockPage) {
        model.addAttribute("startBlockPage", startAndEndBlockPage[0]);
        model.addAttribute("endBlockPage", startAndEndBlockPage[1]);
        model.addAttribute("graduator", graduator);
    }
}
