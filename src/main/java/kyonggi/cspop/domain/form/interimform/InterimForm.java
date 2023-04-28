package kyonggi.cspop.domain.form.interimform;

import kyonggi.cspop.domain.entity.BaseEntity;
import kyonggi.cspop.domain.uploadfile.InterimFormUploadFile;
import kyonggi.cspop.domain.users.Users;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class InterimForm extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private boolean approval;
    @Column
    private String title;
    @Column
    private String division;
    @Column
    private String text;
    @Column
    private String plan;

    @Column
    private boolean rejection;

    @Column
    private String reject_reason;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "interimForm")
    private Users users;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "interimFormUploadFile_id", foreignKey = @ForeignKey(name = "fk_interim_form_upload_file_to_interim_form"))
    private InterimFormUploadFile interimFormUploadFile;

    public void designateUsers(Users users) {
        this.users = users;
    }

    public static InterimForm createInterimForm(String title, String division, String text, String plan, InterimFormUploadFile uploadFile) {

        InterimForm interimForm = new InterimForm();
        interimForm.approval = false;
        interimForm.title = title;

        if (division.equals("option1")) {
            interimForm.division = "구현논문";
        }
        else {
            interimForm.division = "조사(이론)논문";
        }

        interimForm.text = text;
        interimForm.plan = plan;
        interimForm.rejection=false;
        interimForm.interimFormUploadFile = uploadFile;

        return interimForm;
    }

    public void updateInterimForm(String title, String division, String text, String plan) {

        this.title = title;

        if (division.equals("option1")) {
            this.division = "구현논문";
        } else {
            this.division = "조사(이론)논문";
        }
        this.text = text;
        this.plan = plan;
        this.rejection=false;
    }

    public void updateState() {
        this.approval = true;
    }

    public void rejectInterimForm(String reject_reason) {
        this.rejection=true;
        this.reject_reason=reject_reason;
    }
}
