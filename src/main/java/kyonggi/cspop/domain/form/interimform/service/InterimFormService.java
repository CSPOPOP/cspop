package kyonggi.cspop.domain.form.interimform.service;

import kyonggi.cspop.application.controller.form.FormRejectionDto;
import kyonggi.cspop.application.controller.form.interimForm.InterimFormDto;
import kyonggi.cspop.domain.form.interimform.InterimForm;
import kyonggi.cspop.domain.form.interimform.repository.InterimFormRepository;
import kyonggi.cspop.domain.uploadfile.InterimFormUploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InterimFormService {

    private final InterimFormRepository interimFormRepository;

    public InterimForm findInterimForm(Long id) {
        InterimForm interimForm = interimFormRepository.findById(id).get();
        interimForm.getInterimFormUploadFile().getUploadFileName();
        return interimForm;
    }

    @Transactional
    public Long saveInterimForm(InterimForm interimForm) {
        InterimForm saveForm = interimFormRepository.save(interimForm);
        return saveForm.getId();
    }

    @Transactional
    public void updateUserInterimForm(Long id, InterimFormDto interimFormDto, InterimFormUploadFile file) {
        InterimForm interimForm = interimFormRepository.findById(id).get();
        interimForm.updateInterimForm(interimFormDto.getTitle(), interimFormDto.getDivision(), interimFormDto.getText(), interimFormDto.getPlan());
        interimForm.getInterimFormUploadFile().updateFile(file);
    }


    @Transactional
    public void updateUserInterimState(Long id) {
        InterimForm interimForm = interimFormRepository.findById(id).get();
        interimForm.updateState();
    }

    @Transactional
    public void rejectUserProposalForm(Long id, FormRejectionDto formRejectionDto){
        InterimForm interimForm = interimFormRepository.findById(id).get();
        interimForm.rejectInterimForm(formRejectionDto.getReject_reason());
    }
}
