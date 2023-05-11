package kyonggi.cspop.domain.users;

import kyonggi.cspop.domain.entity.BaseEntity;
import kyonggi.cspop.domain.form.finalform.FinalForm;
import kyonggi.cspop.domain.form.interimform.InterimForm;
import kyonggi.cspop.domain.form.otherform.OtherForm;
import kyonggi.cspop.domain.form.proposalform.ProposalForm;
import kyonggi.cspop.domain.form.submitform.SubmitForm;
import kyonggi.cspop.domain.users.enums.Classification;
import kyonggi.cspop.domain.users.enums.Sex;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "submit_form_id_unique",columnNames = "submitForm_id")})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("학번")
    @Column(nullable = false)
    private String studentId;

    @Comment("학생 비밀번호")
    @Column(nullable = false)
    private String studentPassword;

    @Comment("학생 이름")
    @Column(nullable = false)
    private String studentName;

    @Comment("성별")
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Comment("생일")
    @Column(nullable = false)
    private LocalDate birth;

    @Comment("이메일")
    @Column(nullable = false)
    private String email;

    @Comment("전화번호")
    @Column(nullable = false)
    private String phoneNumber;

    @Comment("신분")
    @Enumerated(EnumType.STRING)
    private Classification classification;

    @Comment("소속학과")
    @Column(nullable = false)
    private String department;

    @Comment("비밀번호 대답")
    @Column(nullable = false)
    private String answerPw;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "submitForm_id", foreignKey = @ForeignKey(name = "fk_users_to_submit_form"))
    private SubmitForm submitForm;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "proposalForm_id", foreignKey = @ForeignKey(name = "fk_users_to_proposal_form"))
    private ProposalForm proposalForm;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "interimForm_id", foreignKey = @ForeignKey(name = "fk_users_to_interim_form"))
    private InterimForm interimForm;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "finalForm_id", foreignKey = @ForeignKey(name = "fk_users_to_final_form"))
    private FinalForm finalForm;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "otherForm_id", foreignKey = @ForeignKey(name = "fk_users_to_other_form"))
    private OtherForm otherForm;

    public static Users createUser(String studentId, String studentPassword, String studentName, Sex sex, LocalDate birth, String email,
                                   String phoneNumber, Classification classification, String department,String answerPw) {

        Users user = new Users();
        user.studentId = studentId;
        user.studentPassword = studentPassword;
        user.studentName = studentName;
        user.sex = sex;
        user.birth = birth;
        user.email = email;
        user.phoneNumber = phoneNumber;
        user.classification = classification;
        user.department = department;
        user.answerPw = answerPw;

        return user;
    }

    public void updatePassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    public void encryptPassword(String encryptPassword) {
        this.studentPassword = encryptPassword;
    }

    public void addSubmitForms(SubmitForm submitForm) {
        this.submitForm = submitForm;
        submitForm.designateUsers(this);
    }

    public void addProposalForms(ProposalForm proposalForm) {
        this.proposalForm = proposalForm;
        proposalForm.designateUsers(this);
    }

    public void addInterimForms(InterimForm interimForm) {
        this.interimForm = interimForm;
        interimForm.designateUsers(this);
    }

    public void addFinalForms(FinalForm finalForm) {
        this.finalForm = finalForm;
        finalForm.designateUsers(this);
    }

    public void addOtherForms(OtherForm otherForm) {
        this.otherForm = otherForm;
        otherForm.designateUsers(this);
    }
}
