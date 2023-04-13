package kyonggi.cspop.application.controller.board.excel.graduate;

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

    @GetMapping("/allStep/search")
    public String searchAll(@RequestParam String word, Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> searchName = excelBoardService.findSearchName(pageable, word);

        if (searchName.isEmpty()) {
            model.addAttribute("errorMessage", true);
            return "graduation/graduator/graduation_list";
        }
        int pageNumber = searchName.getPageable().getPageNumber();
        int totalPages = searchName.getTotalPages();
        int pageBlock = 10;
        int startBlockPage = ((pageNumber) / pageBlock) * pageBlock + 1;
        int endBlockPage = startBlockPage + pageBlock - 1;
        endBlockPage = Math.min(totalPages, endBlockPage);

        model.addAttribute("startBlockPage", startBlockPage);
        model.addAttribute("endBlockPage", endBlockPage);
        model.addAttribute("graduator", searchName);
        return "graduation/graduator/graduation_list";
    }

    @GetMapping("/submitFormStep/search")
    public String searchSubmitStep(@RequestParam String word, Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> searchName = excelBoardService.findDetailSearchName(pageable, word,"신청접수");
        if (searchName.isEmpty()) {
            model.addAttribute("errorMessage", true);
            return "graduation/graduator/step/submitFormStep_list";
        }

        int pageNumber = searchName.getPageable().getPageNumber();
        int totalPages = searchName.getTotalPages();
        int pageBlock = 10;
        int startBlockPage = ((pageNumber) / pageBlock) * pageBlock + 1;
        int endBlockPage = startBlockPage + pageBlock - 1;
        endBlockPage = Math.min(totalPages, endBlockPage);

        model.addAttribute("startBlockPage", startBlockPage);
        model.addAttribute("endBlockPage", endBlockPage);
        model.addAttribute("graduator", searchName);
        return "graduation/graduator/step/submitFormStep_list";
    }

    @GetMapping("/proposalFormStep/search")
    public String searchProposalStep(@RequestParam String word, Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> searchName = excelBoardService.findDetailSearchName(pageable, word,"제안서");
        if (searchName.isEmpty()) {
            model.addAttribute("errorMessage", true);
            return "graduation/graduator/step/proposalFormStep_list";
        }

        int pageNumber = searchName.getPageable().getPageNumber();
        int totalPages = searchName.getTotalPages();
        int pageBlock = 10;
        int startBlockPage = ((pageNumber) / pageBlock) * pageBlock + 1;
        int endBlockPage = startBlockPage + pageBlock - 1;
        endBlockPage = Math.min(totalPages, endBlockPage);

        model.addAttribute("startBlockPage", startBlockPage);
        model.addAttribute("endBlockPage", endBlockPage);
        model.addAttribute("graduator", searchName);
        return "graduation/graduator/step/proposalFormStep_list";
    }

    @GetMapping("/interimFormStep/search")
    public String searchInterimStep(@RequestParam String word, Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> searchName = excelBoardService.findDetailSearchName(pageable, word,"중간보고서");
        if (searchName.isEmpty()) {
            model.addAttribute("errorMessage", true);
            return "graduation/graduator/step/interimFormStep_list";
        }

        int pageNumber = searchName.getPageable().getPageNumber();
        int totalPages = searchName.getTotalPages();
        int pageBlock = 10;
        int startBlockPage = ((pageNumber) / pageBlock) * pageBlock + 1;
        int endBlockPage = startBlockPage + pageBlock - 1;
        endBlockPage = Math.min(totalPages, endBlockPage);

        model.addAttribute("startBlockPage", startBlockPage);
        model.addAttribute("endBlockPage", endBlockPage);
        model.addAttribute("graduator", searchName);
        return "graduation/graduator/step/interimFormStep_list";
    }

    @GetMapping("/finalFormStep/search")
    public String searchFinalStep(@RequestParam String word, Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> searchName = excelBoardService.findDetailSearchName(pageable, word,"최종보고서");
        if (searchName.isEmpty()) {
            model.addAttribute("errorMessage", true);
            return "graduation/graduator/step/finalFormStep_list";
        }

        int pageNumber = searchName.getPageable().getPageNumber();
        int totalPages = searchName.getTotalPages();
        int pageBlock = 10;
        int startBlockPage = ((pageNumber) / pageBlock) * pageBlock + 1;
        int endBlockPage = startBlockPage + pageBlock - 1;
        endBlockPage = Math.min(totalPages, endBlockPage);

        model.addAttribute("startBlockPage", startBlockPage);
        model.addAttribute("endBlockPage", endBlockPage);
        model.addAttribute("graduator", searchName);
        return "graduation/graduator/step/finalFormStep_list";
    }

    @GetMapping("/otherFormStep/search")
    public String searchOtherStep(@RequestParam String word, Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> searchName = excelBoardService.findDetailSearchName(pageable, word,"기타자격");
        if (searchName.isEmpty()) {
            model.addAttribute("errorMessage", true);
            return "graduation/graduator/step/otherFormStep_list";
        }

        int pageNumber = searchName.getPageable().getPageNumber();
        int totalPages = searchName.getTotalPages();
        int pageBlock = 10;
        int startBlockPage = ((pageNumber) / pageBlock) * pageBlock + 1;
        int endBlockPage = startBlockPage + pageBlock - 1;
        endBlockPage = Math.min(totalPages, endBlockPage);

        model.addAttribute("startBlockPage", startBlockPage);
        model.addAttribute("endBlockPage", endBlockPage);
        model.addAttribute("graduator", searchName);
        return "graduation/graduator/step/otherFormStep_list";
    }

    @GetMapping("/finalPass/search")
    public String searchFinalPass(@RequestParam String word, Pageable pageable, Model model) {
        Page<ExcelBoardResponseDto> searchName = excelBoardService.findDetailSearchName(pageable, word,"최종통과");
        if (searchName.isEmpty()) {
            model.addAttribute("errorMessage", true);
            return "graduation/graduator/step/finalPassStep_list";
        }

        int pageNumber = searchName.getPageable().getPageNumber();
        int totalPages = searchName.getTotalPages();
        int pageBlock = 10;
        int startBlockPage = ((pageNumber) / pageBlock) * pageBlock + 1;
        int endBlockPage = startBlockPage + pageBlock - 1;
        endBlockPage = Math.min(totalPages, endBlockPage);

        model.addAttribute("startBlockPage", startBlockPage);
        model.addAttribute("endBlockPage", endBlockPage);
        model.addAttribute("graduator", searchName);
        return "graduation/graduator/step/finalPassStep_list";
    }
}
