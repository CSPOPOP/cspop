package kyonggi.cspop.domain.form.proposalform.service;

import kyonggi.cspop.application.controller.form.FormRejectionDto;
import kyonggi.cspop.application.controller.form.proposalform.ProposalFormDto;
import kyonggi.cspop.domain.form.proposalform.ProposalForm;
import kyonggi.cspop.domain.form.proposalform.repository.ProposalFormRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProposalFormService {

    private final ProposalFormRepository proposalFormRepository;

    public ProposalForm findProposalForm(Long id) {
        return proposalFormRepository.findById(id).get();
    }

    @Transactional
    public Long saveProposalForm(ProposalForm proposalForm) {
        ProposalForm saveForm = proposalFormRepository.save(proposalForm);
        return saveForm.getId();
    }

    @Transactional
    public void updateUserProposalForm(Long id, ProposalFormDto proposalFormDto) {
        ProposalForm proposalForm = proposalFormRepository.findById(id).get();
        proposalForm.updateProposalForm(proposalFormDto.getTitle(), proposalFormDto.getDivision(), proposalFormDto.getKeyword(), proposalFormDto.getText());
    }

    @Transactional
    public void updateUserProposalState(Long id) {
        ProposalForm proposalForm = proposalFormRepository.findById(id).get();
        proposalForm.updateState();
    }

    @Transactional
    public void rejectUserProposalForm(Long id, FormRejectionDto formRejectionDto){
        ProposalForm proposalForm = proposalFormRepository.findById(id).get();
        proposalForm.rejectProposalForm(formRejectionDto.getReject_reason());
    }
}
