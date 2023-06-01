package kyonggi.cspop.domain.board.schedule;

import kyonggi.cspop.domain.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleBoard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("신청접수 본문")
    @Column(nullable = false)
    private String receivedText;

    @Comment("제안서 본문")
    @Column(nullable = false)
    private String proposalText;

    @Comment("중간보고서 본문")
    @Column(nullable = false)
    private String interimReportText;

    @Comment("최종보고서 본문")
    @Column(nullable = false)
    private String finalReportText;

    @Comment("최종통과 본문")
    @Column(nullable = false)
    private String finalPassText;

    @Comment("기타자격 본문")
    @Column(nullable = false)
    private String otherQualificationsText;

    public void updateReceivedText(String receivedText) {
        this.receivedText = receivedText;
    }

    public void updateProposalText(String proposalText) {
        this.proposalText = proposalText;
    }

    public void updateInterimReportText(String interimReportText) {
        this.interimReportText = interimReportText;
    }

    public void updateFinalReportText(String finalReportText) {
        this.finalReportText = finalReportText;
    }

    public void updateFinalPassText(String finalPassText) {
        this.finalPassText = finalPassText;
    }

    public void updateOtherQualificationsText(String otherQualificationsText) {
        this.otherQualificationsText = otherQualificationsText;
    }
}
