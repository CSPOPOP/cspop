package kyonggi.cspop.application.controller.form.interimForm.dto;

import kyonggi.cspop.domain.form.interimform.InterimForm;
import kyonggi.cspop.domain.uploadfile.InterimFormUploadFile;
import lombok.Data;

@Data
public class InterimViewDto {

    private Long id;

    private String title;

    private String division;

    private String text;

    private String plan;
    private InterimFormUploadFile interimFormUploadFile;

    public InterimViewDto(InterimForm interimForm) {
        this.id = interimForm.getId();
        this.title = interimForm.getTitle();
        this.division = interimForm.getDivision();
        this.text = interimForm.getText();
        this.plan = interimForm.getPlan();
        this.interimFormUploadFile = interimForm.getInterimFormUploadFile();
    }
}
