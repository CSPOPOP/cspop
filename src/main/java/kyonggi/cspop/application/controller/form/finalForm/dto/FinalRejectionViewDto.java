package kyonggi.cspop.application.controller.form.finalForm.dto;

import kyonggi.cspop.domain.form.finalform.FinalForm;
import lombok.Data;

@Data
public class FinalRejectionViewDto {

    private Long id;
    private String reject_reason;

    public FinalRejectionViewDto(FinalForm finalForm){
        this.id=finalForm.getId();
        this.reject_reason= finalForm.getReject_reason();
    }
}
