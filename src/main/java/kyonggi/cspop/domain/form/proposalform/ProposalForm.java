package kyonggi.cspop.domain.form.proposalform;

import kyonggi.cspop.domain.entity.BaseEntity;
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
public class ProposalForm extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String studentId;
    @Column
    private String studentName;
    @Column
    private String department;
    @Column
    private String graduationDate;
    @Column
    private String advisor;
    @Column
    private String qualification;
    @Column
    private boolean approval;
    @Column
    private String title;
    @Column
    private String division;
    @Column
    private String keyword;
    @Column
    private String text;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "proposalForm")
    private Users users;

    public void designateUsers(Users users) {
        this.users = users;
    }

    public static ProposalForm createProposalForm(String studentId, String studentName, String department, String graduationDate, String advisor, String qualification, String title, String division, String keyword, String text) {

        ProposalForm proposalForm = new ProposalForm();
        proposalForm.studentId = studentId;
        proposalForm.studentName = studentName;
        proposalForm.department = department;
        proposalForm.graduationDate = graduationDate;
        proposalForm.advisor = advisor;
        proposalForm.qualification = qualification;
        proposalForm.approval = false;
        proposalForm.title = title;

        if (division.equals("option1")) {
            proposalForm.division = "구현논문";
        }
        else{
            proposalForm.division = "조사(이론)논문";
        }
        proposalForm.keyword = keyword;
        proposalForm.text = text;

        return proposalForm;
    }

    public void updateProposalForm(String title, String division, String keyword, String text) {

        this.title = title;

        if (division.equals("option1")) {
            this.division = "구현논문";
        } else {
            this.division = "조사(이론)논문";
        }

        this.keyword = keyword;
        this.text = text;
    }

    public void updateState() {
        this.approval = true;
    }
}
