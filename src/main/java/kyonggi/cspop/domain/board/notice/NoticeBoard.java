package kyonggi.cspop.domain.board.notice;

import kyonggi.cspop.domain.admins.Admins;
import kyonggi.cspop.domain.entity.BaseEntity;
import kyonggi.cspop.domain.uploadfile.NoticeBoardUploadFile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class NoticeBoard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("공지사항 제목")
    @Column(nullable = false)
    private String title;

    @Comment("공지사항 본문")
    @Column(nullable = false)
    private String text;

    @Comment("고정 여부")
    private boolean fixed;

    @Comment("조회수")
    private Integer views;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admins_id", foreignKey = @ForeignKey(name = "fk_notice_board_to_admin"))
    private Admins admins;

    @OneToMany(mappedBy = "noticeBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoticeBoardUploadFile> uploadFiles = new ArrayList<>();

    public static NoticeBoard createNoticeBoard(String title, String text, boolean fixed, Integer views, Admins admins, List<NoticeBoardUploadFile> uploadFiles) {
        NoticeBoard noticeBoard = new NoticeBoard();
        noticeBoard.title = title;
        noticeBoard.text = text;
        noticeBoard.fixed = fixed;
        noticeBoard.views = views;
        noticeBoard.admins = admins;
        noticeBoard.uploadFiles = uploadFiles;
        return noticeBoard;
    }

    public void updateFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public void updateViews() {
        this.views += 1;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateText(String text) {
        this.text = text;
    }

    public void updateUploadFiles(List<NoticeBoardUploadFile> uploadFiles) {

        this.uploadFiles.clear();
        this.uploadFiles.addAll(uploadFiles);
        uploadFiles.stream().forEach(e -> e.designateNoticeBoard(this));
    }
}
