package kyonggi.cspop.application.controller.form.proposalform.dto;

import kyonggi.cspop.domain.form.proposalform.ProposalForm;
import lombok.Data;

@Data
public class ProposalViewDto {

    private Long id;

    private String title;

    private String division;

    private String keyword;

    private String text;

    public ProposalViewDto(ProposalForm proposalForm) {
        this.id = proposalForm.getId();
        this.title = proposalForm.getTitle();
        this.division = proposalForm.getDivision();
        this.keyword = proposalForm.getKeyword();
        this.text = proposalForm.getText();
    }
}
