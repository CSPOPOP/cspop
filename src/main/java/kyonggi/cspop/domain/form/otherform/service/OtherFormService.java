package kyonggi.cspop.domain.form.otherform.service;

import kyonggi.cspop.application.controller.form.FormRejectionDto;
import kyonggi.cspop.application.controller.form.otherform.OtherFormDto;
import kyonggi.cspop.domain.form.otherform.OtherForm;
import kyonggi.cspop.domain.form.otherform.repository.OtherFormRepository;
import kyonggi.cspop.domain.uploadfile.OtherFormUploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OtherFormService {

    private final OtherFormRepository otherFormRepository;

    public OtherForm findOtherForm(Long id){

        OtherForm otherForm = otherFormRepository.findById(id).get();
        otherForm.getOtherFormUploadFile().getUploadFileName();
        return otherForm;
    }
    @Transactional
    public Long saveOtherForm(OtherForm otherForm) {
        OtherForm saveForm = otherFormRepository.save(otherForm);
        return saveForm.getId();
    }

    @Transactional
    public void updateUserOtherForm(Long id, OtherFormDto otherFormDto, OtherFormUploadFile file) {
        OtherForm otherForm = otherFormRepository.findById(id).get();
        otherForm.updateOtherForm(otherFormDto.getTitle(), otherFormDto.getDivision(), otherFormDto.getText());
        otherForm.getOtherFormUploadFile().updateFile(file);
    }
    @Transactional
    public void updateUserOtherState(Long id) {
        OtherForm otherForm = otherFormRepository.findById(id).get();
        otherForm.updateState();
    }

    @Transactional
    public void rejectUserOtherForm(Long id, FormRejectionDto formRejectionDto){
        OtherForm otherForm = otherFormRepository.findById(id).get();
        otherForm.rejectOtherForm(formRejectionDto.getReject_reason());
    }
}
