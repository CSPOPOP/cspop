package kyonggi.cspop.application.controller.form.interimForm.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class InterimFormDto {

    @NotBlank @NotNull
    private String title;

    @NotBlank @NotNull
    private String division;

    @NotBlank @NotNull
    private String text;

    @NotBlank @NotNull
    private String plan;

    @NotNull
    private MultipartFile interimFormUploadFile;
}
