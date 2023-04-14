package kyonggi.cspop.domain.form.finalform.service;

import kyonggi.cspop.application.controller.form.finalForm.FinalFormDto;
import kyonggi.cspop.domain.form.finalform.FinalForm;
import kyonggi.cspop.domain.form.finalform.repository.FinalFormRepository;
import kyonggi.cspop.domain.uploadfile.FinalFormUploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FinalFormService {

    private final FinalFormRepository finalFormRepository;

    public FinalForm findFinalForm(Long id) {
        FinalForm finalForm = finalFormRepository.findById(id).get();
        finalForm.getFinalFormUploadFile().getUploadFileName();
        return finalForm;
    }

    @Transactional
    public Long saveFinalForm(FinalForm finalForm) {
        FinalForm saveForm = finalFormRepository.save(finalForm);
        return saveForm.getId();
    }

    @Transactional
    public void updateUserFinalForm(Long id, FinalFormDto finalFormDto, FinalFormUploadFile file) {
        FinalForm finalForm = finalFormRepository.findById(id).get();
        finalForm.updateFinalForm(finalFormDto.getTitle(), finalFormDto.getDivision(), finalFormDto.getQualification(), finalFormDto.getPageNumber());
        finalForm.getFinalFormUploadFile().updateFile(file);
    }

    @Transactional
    public void updateUserFinalState(Long id) {
        FinalForm finalForm = finalFormRepository.findById(id).get();
        finalForm.updateState();
    }
}
