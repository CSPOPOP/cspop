package kyonggi.cspop.domain.form.otherform;


import kyonggi.cspop.domain.entity.BaseEntity;
import kyonggi.cspop.domain.uploadfile.OtherFormUploadFile;
import kyonggi.cspop.domain.users.Users;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OtherForm extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String division;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "otherFormUploadFile_id", foreignKey = @ForeignKey(name = "fk_other_form_upload_file_to_other_form"))
    private OtherFormUploadFile otherFormUploadFile;

    @Column
    private String text;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "otherForm")
    private Users users;

    @Column
    private boolean approval;

    public void designateUsers(Users users) {
        this.users = users;
    }



    public static OtherForm createOtherForm(String title, String division, String text, OtherFormUploadFile uploadFile) {

        OtherForm otherForm = new OtherForm();
        otherForm.approval = false;
        otherForm.title = title;

        if (division.equals("option1")) {
            otherForm.division = "구현논문";
        }
        else {
            otherForm.division = "조사(이론)논문";
        }

        otherForm.text = text;
        otherForm.otherFormUploadFile = uploadFile;

        return otherForm;
    }

    public void updateOtherForm(String title, String division, String text) {

        this.title = title;

        if (division.equals("option1")) {
            this.division = "구현논문";
        } else {
            this.division = "조사(이론)논문";
        }

        this.text = text;
    }

    public void updateFile(OtherFormUploadFile otherFormUploadFile) {
        this.otherFormUploadFile = otherFormUploadFile;
    }
}
