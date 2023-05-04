package kyonggi.cspop.application.controller.form.submitform.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class SubmitFormDto {

    private String studentId;
    private String studentName;
    private String department;
    private boolean approval;

    @NotBlank @NotNull
    private String qualification;
}
