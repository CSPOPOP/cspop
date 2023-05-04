package kyonggi.cspop.application.controller.form.interimForm;

import kyonggi.cspop.application.SessionFactory;
import kyonggi.cspop.application.controller.board.userstatus.dto.UserDetailDto;
import kyonggi.cspop.application.util.FileStore;
import kyonggi.cspop.domain.board.excel.ExcelBoard;
import kyonggi.cspop.domain.board.excel.service.ExcelBoardService;
import kyonggi.cspop.domain.form.interimform.InterimForm;
import kyonggi.cspop.domain.form.interimform.service.InterimFormService;
import kyonggi.cspop.domain.login.dto.UserSessionDto;
import kyonggi.cspop.domain.uploadfile.InterimFormUploadFile;
import kyonggi.cspop.domain.users.Users;
import kyonggi.cspop.domain.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class InterimFormController {

    private final UsersService usersService;
    private final InterimFormService interimFormService;
    private final ExcelBoardService excelBoardService;

    private final FileStore fileStore;

    @GetMapping("api/interimForm")
    public String saveInterimForm(@SessionAttribute(name = SessionFactory.CSPOP_SESSION_KEY, required = false) UserSessionDto userSessionDto, Model model) {
        Users user = usersService.findUserByStudentId(userSessionDto.getStudentId());
        Optional<ExcelBoard> excelByStudentId = excelBoardService.findExcelByStudentId(user.getStudentId());
        UserDetailDto userDetailDto = getUserDetailDto(user, excelByStudentId);

        model.addAttribute("userDetail", userDetailDto);
        return "graduation/form/interimForm";
    }

    @PostMapping("api/interimForm")
    public String saveInterimFormProgress(@SessionAttribute(name = SessionFactory.CSPOP_SESSION_KEY, required = false) UserSessionDto userSessionDto, @Validated @ModelAttribute InterimFormDto interimFormDto, BindingResult bindingResult, HttpServletRequest request, Model model) throws IOException {
        Users user = usersService.findUserByStudentId(userSessionDto.getStudentId());
        Optional<ExcelBoard> excelByStudentId = excelBoardService.findExcelByStudentId(user.getStudentId());
        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("errorMessage", true);

            UserDetailDto userDetailDto = getUserDetailDto(user, excelByStudentId);
            model.addAttribute("userDetail", userDetailDto);
            return "graduation/form/interimForm";
        }

        //파일 크기 제한 예외처리
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        for (MultipartFile multipartFile : fileMap.values()) {
            if (multipartFile.getSize() > 10485760L) {
                model.addAttribute("errorMessage2", true);
                UserDetailDto userDetailDto = getUserDetailDto(user, excelByStudentId);
                model.addAttribute("userDetail", userDetailDto);
                return "graduation/form/interimForm";
            }
        }
        //중간 보고서 파일 저장
        InterimFormUploadFile interimFormUploadFile = fileStore.storeInterimFile(interimFormDto.getInterimFormUploadFile());

        //중간 보고서 폼 등록
        InterimForm interimForm = InterimForm.createInterimForm(interimFormDto.getTitle(), interimFormDto.getDivision(), interimFormDto.getText(), interimFormDto.getPlan(), interimFormUploadFile);
        Long interimFormId = interimFormService.saveInterimForm(interimForm);

        //유저 테이블 수정
        usersService.updateUserByInterimForm(user.getId(), interimFormId);

        //엑셀보드 업데이트
        excelBoardService.updateExcelByInterimForm(user);

        log.info("폼 = {}", interimFormDto);
        //신청 폼 저장 -> 액셀 업데이트 -> 졸업 진행 상황 테이블 업데이트 -> 신청자 리스트 업데이트
        return "redirect:/api/userStatus";
    }

    @GetMapping("api/attach/interimForm/{interimFormId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long interimFormId) throws MalformedURLException {
        InterimForm interimForm = interimFormService.findInterimForm(interimFormId);
        String storeFileName = interimForm.getInterimFormUploadFile().getStoreFileName();
        String uploadFileName = interimForm.getInterimFormUploadFile().getUploadFileName();

        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(resource);
    }

    private static UserDetailDto getUserDetailDto(Users user, Optional<ExcelBoard> excelByStudentId) {
        return new UserDetailDto(
                user.getStudentId(),
                excelByStudentId.get().getGraduationDate(),
                user.getStudentName(),
                user.getDepartment(),
                excelByStudentId.get().getProfessorName(),
                user.getSubmitForm(),
                excelByStudentId.get().getCapstoneCompletion().equals("이수") ? true : false);
    }
}
