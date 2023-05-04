package kyonggi.cspop.application.controller.form.otherform.dto;

import kyonggi.cspop.domain.form.otherform.OtherForm;
import kyonggi.cspop.domain.uploadfile.OtherFormUploadFile;
import lombok.Data;

@Data
public class OtherViewDto {

    private Long id;

    private String title;

    private String division;

    private String text;

    private OtherFormUploadFile otherFormUploadFile;

    public OtherViewDto(OtherForm otherForm) {
        this.id = otherForm.getId();
        this.title = otherForm.getTitle();
        this.division = otherForm.getDivision();
        this.text = otherForm.getText();
        this.otherFormUploadFile = otherForm.getOtherFormUploadFile();
    }
}
