package kyonggi.cspop.application.controller.form.proposalform.dto;

import kyonggi.cspop.domain.form.proposalform.ProposalForm;
import lombok.Data;

@Data
public class ProposalRejectionViewDto {

    private Long id;
    private String reject_reason;

    public ProposalRejectionViewDto(ProposalForm proposalForm){
        this.id=proposalForm.getId();
        this.reject_reason=proposalForm.getReject_reason();
    }
}
