package kyonggi.cspop.domain.form.submitform;

import kyonggi.cspop.domain.entity.BaseEntity;
import kyonggi.cspop.domain.form.submitform.enums.GraduationRequirements;
import kyonggi.cspop.domain.users.Users;
import kyonggi.cspop.exception.CsPopErrorCode;
import kyonggi.cspop.exception.CsPopException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubmitForm extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("학번")
    @Column(nullable = false)
    private String studentId;

    @Comment("학생 이름")
    @Column(nullable = false)
    private String studentName;

    @Comment("소속학과")
    @Column(nullable = false)
    private String department;

    @Comment("승인여부")
    @Column(nullable = false)
    private boolean approval;

    @Enumerated(EnumType.STRING)
    private GraduationRequirements graduationRequirements;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "submitForm")
    private Users users;

    public void designateUsers(Users users) {
        this.users = users;
    }

    public static SubmitForm createSubmitForm(String studentId, String studentName, String department, String graduationRequirements) {
        SubmitForm submitForm = new SubmitForm();
        submitForm.studentId = studentId;
        submitForm.studentName = studentName;
        submitForm.department = department;
        submitForm.approval = false;

        if (graduationRequirements.equals("기타자격")) {
            submitForm.graduationRequirements = GraduationRequirements.Other_Qualifications;
        }
        else{
            submitForm.graduationRequirements = GraduationRequirements.THESIS;
        }
        return submitForm;
    }

    public void updateSubmitForm(String graduationRequirements) {
        if (graduationRequirements.equals("기타자격")) {
            this.graduationRequirements = GraduationRequirements.Other_Qualifications;
        }
        else if(graduationRequirements.equals("논문")){
            this.graduationRequirements = GraduationRequirements.THESIS;
        }
        else {
            throw new CsPopException(CsPopErrorCode.SUBMIT_NOT_QUALIFICATION);
        }
    }

    public void updateState() {
        this.approval = true;
    }
}
