package kyonggi.cspop.application.controller.form.interimForm;

import kyonggi.cspop.domain.form.interimform.InterimForm;
import lombok.Data;

@Data
public class InterimRejectionViewDto {

    private Long id;
    private String reject_reason;

    public InterimRejectionViewDto(InterimForm interimForm){
        this.id=interimForm.getId();
        this.reject_reason=interimForm.getReject_reason();
    }
}
