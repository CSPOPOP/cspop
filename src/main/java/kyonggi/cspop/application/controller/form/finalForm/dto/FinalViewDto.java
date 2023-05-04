package kyonggi.cspop.application.controller.form.finalForm.dto;

import kyonggi.cspop.domain.form.finalform.FinalForm;
import kyonggi.cspop.domain.uploadfile.FinalFormUploadFile;
import lombok.Data;


@Data
public class FinalViewDto {

    private Long id;

    private String title;

    private String division;

    private String qualification;

    private Integer pageNumber;

    private FinalFormUploadFile finalFormUploadFile;


    public FinalViewDto(FinalForm finalForm) {

        this.id = finalForm.getId();
        this.title = finalForm.getTitle();
        this.division = finalForm.getDivision();
        this.qualification = finalForm.getQualification();
        this.pageNumber = finalForm.getPageNumber();
        this.finalFormUploadFile = finalForm.getFinalFormUploadFile();
    }
}
