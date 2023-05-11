package kyonggi.cspop.application.util;

import kyonggi.cspop.domain.board.certification.CertificationBoard;
import kyonggi.cspop.domain.board.certification.service.CertificationBoardService;
import kyonggi.cspop.domain.board.excel.ExcelBoard;
import kyonggi.cspop.domain.board.excel.service.ExcelBoardService;
import kyonggi.cspop.exception.CsPopErrorCode;
import kyonggi.cspop.exception.CsPopException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ExcelStore {

    private final ExcelBoardService excelBoardService;

    private final CertificationBoardService certificationBoardService;

    public ResponseEntity<InputStreamResource> downloadExcelFile() throws IOException {
        File tmpFile = getTmpFile();
        InputStream excelFile = getExcelFile(tmpFile);
        return ResponseEntity.ok().contentLength(tmpFile.length()).contentType(MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition", "attachment;filename=graduation.xlsx").body(new InputStreamResource(excelFile));
    }

    public ResponseEntity<InputStreamResource> downloadCertificationExcelFile() throws IOException {
        File tmpFile = getCertificationTmpFile();
        InputStream certificationExcelFile = getCertificationExcelFile(tmpFile);
        return ResponseEntity.ok().contentLength(tmpFile.length()).contentType(MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition", "attachment;filename=certification.xlsx").body(new InputStreamResource(certificationExcelFile));
    }

    private File getTmpFile() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        File tmpFile = getFile(workbook);
        OutputStream fos = new FileOutputStream(tmpFile);
        workbook.write(fos);
        return tmpFile;
    }

    private static InputStream getExcelFile(File tmpFile) throws FileNotFoundException {
        return new FileInputStream(tmpFile) {
            @Override
            public void close() throws IOException {
                super.close();
            }
        };
    }

    private File getFile(Workbook workbook) throws IOException {
        Sheet sheet = workbook.createSheet("졸업 대상자 조회");
        int rowNo = 0;
        CellStyle headStyle = getHeadStyle(workbook);
        rowNo = createHeader(sheet, rowNo, headStyle);
        createBody(sheet, rowNo);
        setColumnSize(sheet);
        return File.createTempFile("TMP~", ".xlsx");
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

    private File getCertificationTmpFile() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        File tmpFile = getCertificationFile(workbook);
        OutputStream fos = new FileOutputStream(tmpFile);
        workbook.write(fos);
        return tmpFile;
    }

    private static InputStream getCertificationExcelFile(File tmpFile) throws FileNotFoundException {
        return new FileInputStream(tmpFile) {
            @Override
            public void close() throws IOException {
                super.close();
            }
        };
    }

    private File getCertificationFile(Workbook workbook) throws IOException {
        Sheet sheet = workbook.createSheet("공학인증 사정 조회");
        int rowNo = 0;
        CellStyle headStyle = getCertificationHeadStyle(workbook);
        rowNo = createCertificationHeader(sheet, rowNo, headStyle);
        createCertificationBody(sheet, rowNo);
        setCertificationColumnSize(sheet);
        return File.createTempFile("TMP~", ".xlsx");
    }

    private static void setCertificationColumnSize(Sheet sheet) {
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

    private void createCertificationBody(Sheet sheet, int rowNo) {
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

    private static int createCertificationHeader(Sheet sheet, int rowNo, CellStyle headStyle) {
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

    private static CellStyle getCertificationHeadStyle(Workbook workbook) {
        CellStyle headStyle = workbook.createCellStyle();
        headStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_ORANGE.getIndex());
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        font.setFontHeightInPoints((short) 13);
        headStyle.setFont(font);
        return headStyle;
    }


    public List<CertificationBoard> checkCertificationExcelFile(MultipartFile file) throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        checkUploadCertificationFileExtension(extension);
        Sheet worksheet = getWorksheet(file, extension);
        return getCertificationList(worksheet);
    }

    private static List<CertificationBoard> getCertificationList(Sheet worksheet) {
        List<CertificationBoard> certificationBoardList = new ArrayList<>();
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                StringBuilder row1 = new StringBuilder();
                if (cell == null) {
                    String value = "N/A";
                    cell = row.createCell(j);
                    cell.setCellValue(value);
                } else if (cell.getCellType() == CellType.BLANK) {
                    String value = "N/A";
                    cell.setCellValue(value);
                } else if (cell.getCellType() == CellType.STRING) {
                    String value = cell.getStringCellValue();
                    if (value == null || value.trim().isEmpty()) {
                        String replacementValue = "N/A";
                        cell.setCellValue(replacementValue);
                    }
                } else if (cell.getCellType() == CellType.NUMERIC) {
                    double value = cell.getNumericCellValue();
                    String stringValue = String.valueOf(value);

                    if (stringValue.trim().isEmpty()) {
                        String replacementValue = "N/A";
                        cell.setCellValue(replacementValue);
                    } else {
                        if (stringValue.length() > 8) {
                            for (int k = 0; k < stringValue.length(); k++) {
                                char c = stringValue.charAt(k);

                                if (c == '.') continue;

                                if (c >= 'A' && c <= 'Z') break;

                                row1.append(c);
                            }
                            if (row1.length() == 8) {
                                row1.append("0");
                            } else if (row1.length() == 7) {
                                row1.append("00");
                            } else if (row1.length() == 6) {
                                row1.append("000");
                            } else if (row1.length() == 5) {
                                row1.append("0000");
                            }
                            else if(row1.length()==4) {
                                row1.append("00000");
                            }
                            else if(row1.length()==3) {
                                row1.append("000000");
                            }
                        } else {
                            if (!stringValue.contains(".0")) {
                                row1.append(stringValue);
                            } else {
                                for (int k = 0; k < stringValue.length(); k++) {
                                    char c = stringValue.charAt(k);

                                    if (c == '.') break;

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
        return Objects.requireNonNull(workbook).getSheetAt(0);
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
