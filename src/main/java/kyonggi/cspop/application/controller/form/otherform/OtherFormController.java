package kyonggi.cspop.application.controller.form.otherform;

import kyonggi.cspop.application.SessionFactory;
import kyonggi.cspop.application.controller.board.userstatus.dto.UserDetailDto;
import kyonggi.cspop.application.controller.form.otherform.dto.OtherFormDto;
import kyonggi.cspop.application.util.common.FileHandler;
import kyonggi.cspop.domain.board.excel.ExcelBoard;
import kyonggi.cspop.domain.board.excel.service.ExcelBoardService;
import kyonggi.cspop.domain.form.otherform.OtherForm;
import kyonggi.cspop.domain.form.otherform.service.OtherFormService;
import kyonggi.cspop.domain.login.dto.UserSessionDto;
import kyonggi.cspop.domain.uploadfile.OtherFormUploadFile;
import kyonggi.cspop.domain.users.Users;
import kyonggi.cspop.domain.users.service.UsersService;
import lombok.RequiredArgsConstructor;
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
public class OtherFormController {

    private final UsersService usersService;
    private final OtherFormService otherFormService;
    private final ExcelBoardService excelBoardService;

    private final FileHandler fileHandler;

    @GetMapping("api/otherForm")
    public String saveOtherForm(@SessionAttribute(name = SessionFactory.CSPOP_SESSION_KEY, required = false) UserSessionDto userSessionDto, Model model) {
        Users user = usersService.findUserByStudentId(userSessionDto.getStudentId());
        Optional<ExcelBoard> excelByStudentId = excelBoardService.findExcelByStudentId(user.getStudentId());
        UserDetailDto userDetailDto = getUserDetailDto(user, excelByStudentId);

        model.addAttribute("userDetail", userDetailDto);
        return "graduation/form/otherForm";
    }

    @PostMapping("api/otherForm")
    public String saveOtherFormProgress(@SessionAttribute(name = SessionFactory.CSPOP_SESSION_KEY, required = false) UserSessionDto userSessionDto, @Validated @ModelAttribute OtherFormDto otherFormDto, BindingResult bindingResult, HttpServletRequest request, Model model) throws IOException {

        Users user = usersService.findUserByStudentId(userSessionDto.getStudentId());
        Optional<ExcelBoard> excelByStudentId = excelBoardService.findExcelByStudentId(user.getStudentId());


        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("errorMessage", true);

            UserDetailDto userDetailDto = getUserDetailDto(user, excelByStudentId);
            model.addAttribute("userDetail", userDetailDto);
            return "graduation/form/otherForm";
        }
        String x = exceptionOfFile((MultipartHttpServletRequest) request, model, user, excelByStudentId);
        if (x != null) return x;
        OtherFormUploadFile otherFormUploadFile = fileHandler.storeOtherFile(otherFormDto.getOtherFormUploadFile());
        OtherForm otherForm = OtherForm.createOtherForm(otherFormDto.getTitle(), otherFormDto.getDivision(), otherFormDto.getText(), otherFormUploadFile);
        Long otherFormId = otherFormService.saveOtherForm(otherForm);
        usersService.updateUserByOtherForm(user.getId(), otherFormId);
        excelBoardService.updateExcelByOtherForm(user);
        return "redirect:/api/userStatus";
    }

    @GetMapping("api/attach/otherForm/{otherFormId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long otherFormId) throws MalformedURLException {
        OtherForm otherForm = otherFormService.findOtherForm(otherFormId);
        String storeFileName = otherForm.getOtherFormUploadFile().getStoreFileName();
        String uploadFileName = otherForm.getOtherFormUploadFile().getUploadFileName();

        UrlResource resource = new UrlResource("file:" + fileHandler.getFullPath(storeFileName));

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
                excelByStudentId.get().getCapstoneCompletion().equals("이수"));
    }
    private static String exceptionOfFile(MultipartHttpServletRequest request, Model model, Users user, Optional<ExcelBoard> excelByStudentId) {
        Map<String, MultipartFile> fileMap = request.getFileMap();

        for (MultipartFile multipartFile : fileMap.values()) {
            if (multipartFile.getSize() > 10485760L) {
                model.addAttribute("errorMessage2", true);
                UserDetailDto userDetailDto = getUserDetailDto(user, excelByStudentId);
                model.addAttribute("userDetail", userDetailDto);
                return "graduation/form/interimForm";
            }
        }
        return null;
    }
}

