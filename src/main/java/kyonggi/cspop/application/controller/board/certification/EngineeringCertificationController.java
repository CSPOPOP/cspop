package kyonggi.cspop.application.controller.board.certification;

import kyonggi.cspop.application.util.common.ExcelHandler;
import kyonggi.cspop.application.util.common.PageHandler;
import kyonggi.cspop.domain.board.certification.CertificationBoard;
import kyonggi.cspop.domain.board.certification.dto.CertificationBoardResponseDto;
import kyonggi.cspop.domain.board.certification.service.CertificationBoardService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/graduation")
public class EngineeringCertificationController {

    private final CertificationBoardService certificationBoardService;
    private final PageHandler pageHandler;
    private final ExcelHandler excelHandler;

    @GetMapping("/certification_management")
    public String certificationForm(Pageable pageable, Model model) {
        Page<CertificationBoardResponseDto> allCertificationBoard = certificationBoardService.findAllCertificationBoard(pageable);
        int[] startAndEndBlockPage = pageHandler.getStartAndEndBlockPage(allCertificationBoard.getPageable().getPageNumber(), allCertificationBoard.getTotalPages());
        putPagingInf(model, allCertificationBoard, startAndEndBlockPage);
        return "graduation/certification/certification_list";
    }

    @PostMapping("/certification_management.read")
    public String uploadCertificationExcelFile(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        deleteExistentFileAndUploadCertificationExcelFile(file, model);
        return "redirect:./certification_management?page=0&size=10";
    }

    @SneakyThrows
    @GetMapping("/certification_management.download")
    public ResponseEntity<InputStreamResource> downloadCertificationExcelFile() {
        File certificationTmpFile = excelHandler.getCertificationTmpFile();
        InputStream certificationExcelFile = excelHandler.getCertificationExcelFile(certificationTmpFile);
        return ResponseEntity.ok().contentLength(certificationTmpFile.length()).contentType(MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition", "attachment;filename=certification.xlsx").body(new InputStreamResource(certificationExcelFile));
    }

    private static void putPagingInf(Model model, Page<CertificationBoardResponseDto> allCertificationBoard, int[] startAndEndBlockPage) {
        model.addAttribute("startBlockPage", startAndEndBlockPage[0]);
        model.addAttribute("endBlockPage", startAndEndBlockPage[1]);
        model.addAttribute("certification", allCertificationBoard);
    }

    private void deleteExistentFileAndUploadCertificationExcelFile(MultipartFile file, Model model) throws IOException {
        List<CertificationBoard> certificationBoardList = excelHandler.checkCertificationExcelFile(file);
        certificationBoardService.deleteExcelListAndUploadCertificationList(certificationBoardList);
        model.addAttribute("certification", certificationBoardList);
    }
}
