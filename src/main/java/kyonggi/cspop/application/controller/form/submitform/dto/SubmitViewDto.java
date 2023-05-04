package kyonggi.cspop.application.controller.form.submitform.dto;

import kyonggi.cspop.domain.form.submitform.SubmitForm;
import lombok.Data;

@Data
public class SubmitViewDto {

    private Long id;
    private String qualification;

    public SubmitViewDto(SubmitForm submitForm) {
        this.id = submitForm.getId();
        this.qualification = submitForm.getGraduationRequirements().getGraduationRequirementsToString();
    }
}
