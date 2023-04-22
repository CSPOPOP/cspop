package kyonggi.cspop.domain.board.excel;

import kyonggi.cspop.domain.entity.BaseEntity;
import kyonggi.cspop.domain.form.submitform.SubmitForm;
import kyonggi.cspop.domain.form.submitform.enums.GraduationRequirements;
import kyonggi.cspop.domain.schedule.enums.Step;
import kyonggi.cspop.domain.users.Users;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter @Setter
@DynamicUpdate
public class ExcelBoard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("학번")
    private String studentId;
    @Comment("학생 이름")
    private String studentName;
    @Comment("교수 이름")
    private String professorName;
    @Comment("학생 졸업날짜")
    private String graduationDate;
    @Comment("단계")
    private String step;
    @Comment("상태")
    private String state;
    @Comment("자격")
    private String qualifications;
    @Comment("캡스톤 이수")
    private String capstoneCompletion;

    public static ExcelBoard addExcelBySubmitForm(Users users, SubmitForm submitForm) {

        ExcelBoard excelBoard = new ExcelBoard();
        excelBoard.studentId = users.getStudentId();
        excelBoard.studentName = users.getStudentName();
        excelBoard.professorName = "미정";
        excelBoard.graduationDate = "미확인";
        excelBoard.step = Step.RECEIVED.getStepToString();
        excelBoard.state = "미승인";

        if (submitForm.getGraduationRequirements().getGraduationRequirementsToString().equals("기타자격")) {
            excelBoard.qualifications = GraduationRequirements.Other_Qualifications.getGraduationRequirementsToString();
        }
        else {
            excelBoard.qualifications = GraduationRequirements.THESIS.getGraduationRequirementsToString();
        }
        excelBoard.capstoneCompletion = "미확인";
        return excelBoard;
    }

    public void updateExcelByProposalForm() {
        this.step = Step.PROPOSAL.getStepToString();
        this.state = "미승인";
    }

    public void updateExcelByInterimForm() {
        this.step = Step.INTERIM_REPORT.getStepToString();
        this.state = "미승인";
    }

    public void updateExcelByFinalForm(){
        this.step = Step.FINAL_REPORT.getStepToString();
        this.state = "미승인";
    }

    public void updateExcelByOtherForm(){
        this.step = Step.OTHER_QUALIFICATIONS.getStepToString();
        this.state = "미승인";
    }

    public void updateExcelBySubmitForm(String professorName, String graduationDate, String capstoneCompletion) {
        this.professorName = professorName;
        this.graduationDate = graduationDate;
        this.state = "승인";

        if (capstoneCompletion.equals("option1")) {
            this.capstoneCompletion = "이수";
        }
        else{
            this.capstoneCompletion = "미이수";
        }
    }

    public void approvalUserForm() {
        this.state = "승인";
    }

    public void updateQualification() {

        if (qualifications.equals("기타자격")) {
            this.qualifications = "논문";
        }
        else{
            this.qualifications = "기타자격";
        }
    }
}
