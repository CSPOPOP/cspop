package kyonggi.cspop.application.controller.board.excel.certification;

import kyonggi.cspop.domain.board.CertificationBoard;
import kyonggi.cspop.domain.board.dto.CertificationBoardResponseDto;
import kyonggi.cspop.domain.board.service.CertificationBoardService;
import kyonggi.cspop.exception.CsPopErrorCode;
import kyonggi.cspop.exception.CsPopException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//공학인증 액셀 게시판 컨트롤러
@Controller
@RequiredArgsConstructor
@RequestMapping("api/graduation")
public class CertificationExcelBoardController {

    private final CertificationBoardService certificationBoardService;

    @GetMapping("/certification_management")
    public String certificationForm(Pageable pageable, Model model) {
        Page<CertificationBoardResponseDto> allCertificationBoard = certificationBoardService.findAllCertificationBoard(pageable);

        int pageNumber = allCertificationBoard.getPageable().getPageNumber();
        int totalPages = allCertificationBoard.getTotalPages();
        int pageBlock = 10;
        int startBlockPage = ((pageNumber) / pageBlock) * pageBlock + 1;
        int endBlockPage = startBlockPage + pageBlock - 1;
        endBlockPage = Math.min(totalPages, endBlockPage);

        model.addAttribute("startBlockPage", startBlockPage);
        model.addAttribute("endBlockPage", endBlockPage);
        model.addAttribute("graduator", allCertificationBoard);
        return "graduation/certification/certification_list";
    }

    @PostMapping("/certification_management.read")
    public String uploadCertification(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        //액셀 파일인지 검사
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        checkUploadFileExtension(extension);

        //업로드 된 Excel 파일의 데이터를 ExcelBoard 객체 리스트 형태로 저장 (액셀 파일의 문자만 받고, 숫자는 못받는 버그 수정해야함)
        Sheet worksheet = getWorksheet(file, extension);
        List<CertificationBoard> certificationBoardList = getCertificationList(worksheet);

        certificationBoardService.deleteExcelListAndUploadExcelList(certificationBoardList);

        model.addAttribute("graduator", certificationBoardList);
        return "redirect:./certification_management?page=0&size=10";
    }

    @SneakyThrows
    @GetMapping("/certification_management.download")
    public ResponseEntity<InputStreamResource> downloadCertification(HttpServletResponse response) {

        File tmpFile = getTmpFile();
        InputStream excelFile = getExcelFile(tmpFile);

        return ResponseEntity.ok() //
                .contentLength(tmpFile.length()) //
                .contentType(MediaType.APPLICATION_OCTET_STREAM) //
                .header("Content-Disposition", "attachment;filename=graduation.xlsx") //
                .body(new InputStreamResource(excelFile));
    }

    /**
     * 호출 함수 정의, 프론트 작업자 x
     *
     */

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
        Sheet sheet = workbook.createSheet("공학인증 사정 조회");
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
        List<CertificationBoard> dataList = certificationBoardService.findCertificationList();
        for (CertificationBoard certificationBoard : dataList) {
            Row row = sheet.createRow(rowNo++);
            row.createCell(0).setCellValue(certificationBoard.getDepartment());
            row.createCell(1).setCellValue(certificationBoard.getStudentId());
            row.createCell(2).setCellValue(certificationBoard.getStudentName());
            row.createCell(3).setCellValue(certificationBoard.getCurrentSemester());
            row.createCell(4).setCellValue(certificationBoard.getProfessionalEducation());
            row.createCell(5).setCellValue(certificationBoard.getMscBsm());
            row.createCell(6).setCellValue(certificationBoard.getDesign());
            row.createCell(7).setCellValue(certificationBoard.getMajor());
            row.createCell(8).setCellValue(certificationBoard.getEssential());
            row.createCell(9).setCellValue(certificationBoard.getFirstAndLast());
            row.createCell(10).setCellValue(certificationBoard.getTotal());
            row.createCell(11).setCellValue(certificationBoard.getSpecialNote());

        }
    }

    private static int createHeader(Sheet sheet, int rowNo, CellStyle headStyle) {
        Row headerRow = sheet.createRow(rowNo++);
        headerRow.createCell(0).setCellValue("소속 학과");
        headerRow.createCell(1).setCellValue("학번");
        headerRow.createCell(2).setCellValue("학생 이름");
        headerRow.createCell(3).setCellValue("현재 학기");
        headerRow.createCell(4).setCellValue("전문교양 학점");
        headerRow.createCell(5).setCellValue("MSC/BSM 학점");
        headerRow.createCell(6).setCellValue("설계 학점");
        headerRow.createCell(7).setCellValue("전공 학점");
        headerRow.createCell(8).setCellValue("필수 과목");
        headerRow.createCell(9).setCellValue("선/후수 과목");
        headerRow.createCell(10).setCellValue("총 학점");
        headerRow.createCell(11).setCellValue("특이 사항");

        for (int i = 0; i <= 11; i++) {
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

    private static List<CertificationBoard> getCertificationList(Sheet worksheet) {
        List<CertificationBoard> certificationBoardList = new ArrayList<>();
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);

            //들어온 값이 문자가 아닐 경우 문자열로 변환 (row 1 or 7)
            for (Cell cell : row) {

                if (cell.getCellType() != CellType.STRING) {
                    double value = cell.getNumericCellValue();
                    String stringValue = String.valueOf(value);

                    StringBuilder row1 = new StringBuilder();

                    if (stringValue.length()>5){
                        for (int j=0;j<stringValue.length();j++) {
                            char c = stringValue.charAt(j);

                            if (c=='.')
                                continue;

                            if (c>='A' && c<='Z')
                                break;

                            row1.append(c);
                        }
                    }
                    else{
                        for (int j=0;j<stringValue.length();j++) {
                            char c = stringValue.charAt(j);

                            if (c == '.')
                                break;

                            row1.append(c);
                        }
                    }
                    cell.setCellValue(row1.toString());
                }
            }
            CertificationBoard data = CertificationBoard.createCertificationBoard(row);
            certificationBoardList.add(data);
        }
        return certificationBoardList;
    }

    private static Sheet getWorksheet(MultipartFile file, String extension) throws IOException {
        Workbook workbook = getWorkbook(file, extension);
        Sheet worksheet = Objects.requireNonNull(workbook).getSheetAt(0);
        return worksheet;
    }

    private static Workbook getWorkbook(MultipartFile file, String extension) throws IOException {
        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }
        return workbook;
    }

    private static void checkUploadFileExtension(String extension) {

        if (extension.equals("")) {
            throw new CsPopException(CsPopErrorCode.NO_UPLOAD_FILE_EXTENSION);
        }
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new CsPopException(CsPopErrorCode.INVALID_UPLOAD_FILE_EXTENSION);
        }
    }
}
