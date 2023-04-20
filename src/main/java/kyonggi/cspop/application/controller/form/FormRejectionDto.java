package kyonggi.cspop.application.controller.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class FormRejectionDto {

    @NotBlank @NotNull
    private String reject_reason;
}
