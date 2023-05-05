package kyonggi.cspop.application.controller.form.otherform.dto;

import kyonggi.cspop.domain.form.otherform.OtherForm;
import lombok.Data;

@Data
public class OtherRejectionViewDto {

    private Long id;
    private String reject_reason;

    public OtherRejectionViewDto(OtherForm otherForm){
        this.id=otherForm.getId();
        this.reject_reason=otherForm.getReject_reason();
    }
}
