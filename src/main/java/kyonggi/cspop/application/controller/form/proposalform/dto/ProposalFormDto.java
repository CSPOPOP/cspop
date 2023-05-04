package kyonggi.cspop.application.controller.form.proposalform.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProposalFormDto {

    @NotBlank @NotNull
    private String title;

    @NotBlank @NotNull
    private String division;

    @NotBlank @NotNull
    private String keyword;

    @NotBlank @NotNull
    private String text;
}
