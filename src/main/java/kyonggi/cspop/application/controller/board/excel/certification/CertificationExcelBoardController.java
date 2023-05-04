package kyonggi.cspop.application.controller.board.excel.certification;

import kyonggi.cspop.domain.board.certification.CertificationBoard;
import kyonggi.cspop.domain.board.certification.dto.CertificationBoardResponseDto;
import kyonggi.cspop.domain.board.certification.service.CertificationBoardService;
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
        model.addAttribute("certification", allCertificationBoard);
        return "graduation/certification/certification_list";
    }

    @PostMapping("/certification_management.read")
    public String uploadCertification(@RequestParam("file") MultipartFile file, Model model) throws IOException {

        List<CertificationBoard> certificationBoardList = checkExcelFile(file);
        certificationBoardService.deleteExcelListAndUploadCertificationList(certificationBoardList);

        model.addAttribute("certification", certificationBoardList);
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
                .header("Content-Disposition", "attachment;filename=certification.xlsx") //
                .body(new InputStreamResource(excelFile));
    }

    /**
     * 호출 함수 정의, 프론트 작업자 x
     *
     */

    private static List<CertificationBoard> checkExcelFile(MultipartFile file) throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        checkUploadCertificationFileExtension(extension);

        Sheet worksheet = getWorksheet(file, extension);
        return getCertificationList(worksheet);
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
        sheet.setColumnWidth(0, 3500);
        sheet.setColumnWidth(1, 3000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 3000);
        sheet.setColumnWidth(4, 2500);
        sheet.setColumnWidth(5, 2500);
        sheet.setColumnWidth(6, 2500);
        sheet.setColumnWidth(7, 2500);
        sheet.setColumnWidth(8, 2500);
        sheet.setColumnWidth(9, 2500);
        sheet.setColumnWidth(10, 2500);
        sheet.setColumnWidth(11, 8000);
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
        headerRow.createCell(4).setCellValue("전문교양");
        headerRow.createCell(5).setCellValue("MSC/BSM");
        headerRow.createCell(6).setCellValue("설계");
        headerRow.createCell(7).setCellValue("전공");
        headerRow.createCell(8).setCellValue("필수");
        headerRow.createCell(9).setCellValue("선/후수");
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

            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);

                StringBuilder row1 = new StringBuilder();

                //null 또는 빈 값일 경우 대체 값 대입
                if (cell == null) {
                    String value = "N/A";
                    cell = row.createCell(j);
                    cell.setCellValue(value);
                }
                else if (cell.getCellType() == CellType.BLANK) {
                    String value = "N/A";
                    cell.setCellValue(value);
                }
                else if (cell.getCellType() == CellType.STRING) {
                    String value = cell.getStringCellValue();
                    if (value == null || value.trim().isEmpty()) {
                        // 빈 문자열인 경우 대체 값을 설정하고 셀에 입력
                        String replacementValue = "N/A";
                        cell.setCellValue(replacementValue);
                    }
                }

                else if (cell.getCellType() == CellType.NUMERIC) {
                    double value = cell.getNumericCellValue();
                    String stringValue = String.valueOf(value);

                    //들어온 값이 빈 값
                    if (stringValue.trim().isEmpty()) {
                        String replacementValue = "N/A";
                        cell.setCellValue(replacementValue);
                    }
                    else{
                        if (stringValue.length()>8){
                            for (int k=0;k<stringValue.length();k++) {
                                char c = stringValue.charAt(k);

                                if (c=='.')
                                    continue;

                                if (c>='A' && c<='Z')
                                    break;

                                row1.append(c);
                            }
                            //학번 뒷자리에 0이 연속해서 올 경우 있는 만큼 0 추가
                            if (row1.length() == 8) {
                                row1.append("0");
                            }
                            else if (row1.length() == 7) {
                                row1.append("00");
                            }
                            else if (row1.length() == 6) {
                                row1.append("000");
                            }
                            //이외의 경우는 없다고 봄
                        }
                        else {
                            //설계 학점은 따로 반환
                            if (!stringValue.contains(".0")) {
                                row1.append(stringValue);
                            }
                            else {
                                for (int k = 0; k < stringValue.length(); k++) {
                                    char c = stringValue.charAt(k);

                                    if (c == '.')
                                        break;

                                    row1.append(c);
                                }
                            }
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

    private static void checkUploadCertificationFileExtension(String extension) {

        if (extension.equals("")) {
            throw new CsPopException(CsPopErrorCode.NO_UPLOAD_FILE_EXTENSION);
        }
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new CsPopException(CsPopErrorCode.INVALID_UPLOAD_FILE_EXTENSION);
        }
    }
}
