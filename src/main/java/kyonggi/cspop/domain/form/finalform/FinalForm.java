package kyonggi.cspop.domain.form.finalform;

import kyonggi.cspop.domain.entity.BaseEntity;
import kyonggi.cspop.domain.uploadfile.FinalFormUploadFile;
import kyonggi.cspop.domain.users.Users;
import kyonggi.cspop.exception.CsPopErrorCode;
import kyonggi.cspop.exception.CsPopException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class FinalForm extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "finalForm")
    private Users users;

    @Column
    private boolean approval;

    @Column
    private String title;

    @Column
    private String division;

    @Column
    private String qualification;

    @Column
    private Integer pageNumber;

    @Column
    private boolean rejection;

    @Column
    private String reject_reason;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "finalFormUploadFile_id", foreignKey = @ForeignKey(name = "fk_final_form_upload_file_to_final_form"))
    private FinalFormUploadFile finalFormUploadFile;

    public void designateUsers(Users users) {
        this.users = users;
    }

    public static FinalForm createFinalForm(String title, String division, String qualification, Integer pageNumber, FinalFormUploadFile uploadFile) {

        FinalForm finalForm = new FinalForm();
        finalForm.approval = false;
        finalForm.title = title;

        if (division.equals("option1")) {
            finalForm.division = "구현논문";
        }
        else {
            finalForm.division = "조사(이론)논문";
        }

        if (qualification.equals("option1")) {
            finalForm.qualification = "논문양식파일사용";
        }
        else if (qualification.equals("option2")) {
            finalForm.qualification = "목차,서론,본론,결론,참고문헌 포함";
        }
        else{
            finalForm.qualification = "본인이 직접 작성한 파일임을 확인함";
        }

        finalForm.pageNumber = pageNumber;
        finalForm.rejection=false;
        finalForm.finalFormUploadFile = uploadFile;

        return finalForm;
    }

    public void updateFinalForm(String title, String division, String qualification, Integer pageNumber) {

        this.title = title;

        if (division.equals("option1")) {
            this.division = "구현논문";
        } else {
            this.division = "조사(이론)논문";
        }

        if (qualification.equals("option1")) {
            this.qualification = "논문양식파일사용";
        } else if (qualification.equals("option2")) {
            this.qualification = "목차,서론,본론,결론,참고문헌 포함";
        } else {
            this.qualification = "본인이 직접 작성한 파일임을 확인함";
        }

        if (pageNumber <= 0) {
            throw new CsPopException(CsPopErrorCode.FINAL_INVALID_PAGE);
        }
        this.pageNumber = pageNumber;
        this.rejection=false;
    }

    public void updateState() {
        this.approval = true;
    }

    public void rejectFinalForm(String reject_reason){
        this.rejection=true;
        this.reject_reason=reject_reason;
    }
}
