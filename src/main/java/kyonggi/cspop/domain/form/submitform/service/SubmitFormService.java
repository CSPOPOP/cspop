package kyonggi.cspop.domain.form.submitform.service;

import kyonggi.cspop.application.controller.form.submitform.dto.SubmitFormDto;
import kyonggi.cspop.domain.form.submitform.SubmitForm;
import kyonggi.cspop.domain.form.submitform.repository.SubmitFormRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubmitFormService {

    private final SubmitFormRepository submitFormRepository;

    public SubmitForm findSubmitForm(Long id) {
        return submitFormRepository.findById(id).get();
    }

    @Transactional
    public Long saveSubmitForm(SubmitForm submitForm) {
        SubmitForm saveForm = submitFormRepository.save(submitForm);
        return saveForm.getId();
    }

    @Transactional
    public void updateUserSubmitForm(Long id, SubmitFormDto submitFormDto) {
        SubmitForm submitForm = submitFormRepository.findById(id).get();
        submitForm.updateSubmitForm(submitFormDto.getQualification());
    }

    @Transactional
    public void updateUserSubmitState(Long id) {
        SubmitForm submitForm = submitFormRepository.findById(id).get();
        submitForm.updateState();
    }
}
