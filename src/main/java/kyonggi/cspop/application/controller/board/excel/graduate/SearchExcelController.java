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
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
@RequestMapping("api/graduation")
public class SearchExcelController {

    private final ExcelBoardService excelBoardService;
    private final PageStore pageStore;

    @GetMapping("/allStep/search")
    public String searchAll(@RequestParam String word, Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> searchName = excelBoardService.findSearchName(pageable, word);
        if (searchName.isEmpty()) {
            model.addAttribute("errorMessage", true);
            return "graduation/graduator/graduation_list";
        }
        int[] startAndEndBlockPage = pageStore.getStartAndEndBlockPage(searchName.getPageable().getPageNumber(), searchName.getTotalPages());
        putPagingInf(model, searchName, startAndEndBlockPage);
        return "graduation/graduator/graduation_list";
    }

    @GetMapping("/submitFormStep/search")
    public String searchSubmitStep(@RequestParam String word, Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> searchName = excelBoardService.findDetailSearchName(pageable, word,"신청접수");
        if (searchName.isEmpty()) {
            model.addAttribute("errorMessage", true);
            return "graduation/graduator/step/submitFormStep_list";
        }
        int[] startAndEndBlockPage = pageStore.getStartAndEndBlockPage(searchName.getPageable().getPageNumber(), searchName.getTotalPages());
        putPagingInf(model, searchName, startAndEndBlockPage);
        return "graduation/graduator/step/submitFormStep_list";
    }

    @GetMapping("/proposalFormStep/search")
    public String searchProposalStep(@RequestParam String word, Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> searchName = excelBoardService.findDetailSearchName(pageable, word,"제안서");
        if (searchName.isEmpty()) {
            model.addAttribute("errorMessage", true);
            return "graduation/graduator/step/proposalFormStep_list";
        }
        int[] startAndEndBlockPage = pageStore.getStartAndEndBlockPage(searchName.getPageable().getPageNumber(), searchName.getTotalPages());
        putPagingInf(model, searchName, startAndEndBlockPage);
        return "graduation/graduator/step/proposalFormStep_list";
    }

    @GetMapping("/interimFormStep/search")
    public String searchInterimStep(@RequestParam String word, Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> searchName = excelBoardService.findDetailSearchName(pageable, word,"중간보고서");
        if (searchName.isEmpty()) {
            model.addAttribute("errorMessage", true);
            return "graduation/graduator/step/interimFormStep_list";
        }
        int[] startAndEndBlockPage = pageStore.getStartAndEndBlockPage(searchName.getPageable().getPageNumber(), searchName.getTotalPages());
        putPagingInf(model, searchName, startAndEndBlockPage);
        return "graduation/graduator/step/interimFormStep_list";
    }

    @GetMapping("/finalFormStep/search")
    public String searchFinalStep(@RequestParam String word, Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> searchName = excelBoardService.findDetailSearchName(pageable, word,"최종보고서");
        if (searchName.isEmpty()) {
            model.addAttribute("errorMessage", true);
            return "graduation/graduator/step/finalFormStep_list";
        }
        int[] startAndEndBlockPage = pageStore.getStartAndEndBlockPage(searchName.getPageable().getPageNumber(), searchName.getTotalPages());
        putPagingInf(model, searchName, startAndEndBlockPage);
        return "graduation/graduator/step/finalFormStep_list";
    }

    @GetMapping("/otherFormStep/search")
    public String searchOtherStep(@RequestParam String word, Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> searchName = excelBoardService.findDetailSearchName(pageable, word,"기타자격");
        if (searchName.isEmpty()) {
            model.addAttribute("errorMessage", true);
            return "graduation/graduator/step/otherFormStep_list";
        }
        int[] startAndEndBlockPage = pageStore.getStartAndEndBlockPage(searchName.getPageable().getPageNumber(), searchName.getTotalPages());
        putPagingInf(model, searchName, startAndEndBlockPage);
        return "graduation/graduator/step/otherFormStep_list";
    }

    @GetMapping("/finalPass/search")
    public String searchFinalPass(@RequestParam String word, Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> searchName = excelBoardService.findDetailSearchName(pageable, word,"최종통과");
        if (searchName.isEmpty()) {
            model.addAttribute("errorMessage", true);
            return "graduation/graduator/step/finalPassStep_list";
        }
        int[] startAndEndBlockPage = pageStore.getStartAndEndBlockPage(searchName.getPageable().getPageNumber(), searchName.getTotalPages());
        putPagingInf(model, searchName, startAndEndBlockPage);
        return "graduation/graduator/step/finalPassStep_list";
    }

    private static void putPagingInf(Model model, Page<ExcelBoardResponseDto> searchName, int[] startAndEndBlockPage) {
        model.addAttribute("startBlockPage", startAndEndBlockPage[0]);
        model.addAttribute("endBlockPage", startAndEndBlockPage[1]);
        model.addAttribute("graduator", searchName);
    }
}
