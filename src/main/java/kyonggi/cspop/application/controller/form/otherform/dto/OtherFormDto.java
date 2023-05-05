package kyonggi.cspop.application.controller.form.otherform.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class OtherFormDto {

    @NotBlank @NotNull
    private String title;

    @NotBlank @NotNull
    private String division;

    @NotBlank @NotNull
    private String text;

    @NotNull
    private MultipartFile otherFormUploadFile;
}
