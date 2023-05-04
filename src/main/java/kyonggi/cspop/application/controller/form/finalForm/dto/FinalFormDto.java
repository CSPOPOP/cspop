package kyonggi.cspop.application.controller.form.finalForm.dto;

import lombok.Data;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class FinalFormDto {

    @NotBlank @NotNull
    private String title;

    @NotBlank @NotNull
    private String division;

    @NotBlank @NotNull
    private String qualification;

    @NotNull
    @NumberFormat(style = NumberFormat.Style.NUMBER )
    private Integer pageNumber;

    @NotNull
    private MultipartFile finalFormUploadFile;
}
