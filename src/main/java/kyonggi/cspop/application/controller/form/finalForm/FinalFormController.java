package kyonggi.cspop.application.controller.form.finalForm;

import kyonggi.cspop.application.SessionFactory;
import kyonggi.cspop.application.controller.board.userstatus.dto.UserDetailDto;
import kyonggi.cspop.application.controller.form.finalForm.dto.FinalFormDto;
import kyonggi.cspop.application.util.common.FileHandler;
import kyonggi.cspop.domain.board.excel.ExcelBoard;
import kyonggi.cspop.domain.board.excel.service.ExcelBoardService;
import kyonggi.cspop.domain.form.finalform.FinalForm;
import kyonggi.cspop.domain.form.finalform.service.FinalFormService;
import kyonggi.cspop.domain.login.dto.UserSessionDto;
import kyonggi.cspop.domain.uploadfile.FinalFormUploadFile;
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
public class FinalFormController {

    private final UsersService usersService;
    private final FinalFormService finalFormService;
    private final ExcelBoardService excelBoardService;

    private final FileHandler fileHandler;

    @GetMapping("api/finalForm")
    public String saveFinalForm(@SessionAttribute(name = SessionFactory.CSPOP_SESSION_KEY, required = false) UserSessionDto userSessionDto, Model model) {
        Users user = usersService.findUserByStudentId(userSessionDto.getStudentId());
        Optional<ExcelBoard> excelByStudentId = excelBoardService.findExcelByStudentId(user.getStudentId());
        UserDetailDto userDetailDto = getUserDetailDto(user, excelByStudentId);

        model.addAttribute("userDetail", userDetailDto);
        return "graduation/form/finalForm";
    }

    @PostMapping("api/finalForm")
    public String saveFinalFormProgress(@SessionAttribute(name = SessionFactory.CSPOP_SESSION_KEY, required = false) UserSessionDto userSessionDto, @Validated @ModelAttribute FinalFormDto finalFormDto, BindingResult bindingResult, HttpServletRequest request, Model model) throws IOException {

        Users user = usersService.findUserByStudentId(userSessionDto.getStudentId());
        Optional<ExcelBoard> excelByStudentId = excelBoardService.findExcelByStudentId(user.getStudentId());

        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("errorMessage", true);

            UserDetailDto userDetailDto = getUserDetailDto(user, excelByStudentId);
            model.addAttribute("userDetail", userDetailDto);
            return "graduation/form/finalForm";
        }

        String x = exceptionOfFile((MultipartHttpServletRequest) request, model, user, excelByStudentId);
        if (x != null)
            return x;
        FinalFormUploadFile finalFormUploadFile = fileHandler.storeFinalFile(finalFormDto.getFinalFormUploadFile());
        FinalForm finalForm = FinalForm.createFinalForm(finalFormDto.getTitle(), finalFormDto.getDivision(), finalFormDto.getQualification(), finalFormDto.getPageNumber(), finalFormUploadFile);
        Long finalFormId = finalFormService.saveFinalForm(finalForm);
        usersService.updateUserByFinalForm(user.getId(), finalFormId);
        excelBoardService.updateExcelByFinalForm(user);
        return "redirect:/api/userStatus";
    }

    @GetMapping("api/attach/finalForm/{finalFormId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long finalFormId) throws MalformedURLException {
        FinalForm finalForm = finalFormService.findFinalForm(finalFormId);
        String storeFileName = finalForm.getFinalFormUploadFile().getStoreFileName();
        String uploadFileName = finalForm.getFinalFormUploadFile().getUploadFileName();
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
