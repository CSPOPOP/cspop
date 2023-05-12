package kyonggi.cspop.domain.admins;

import kyonggi.cspop.domain.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admins extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("관리자 이름")
    @Column(nullable = false)
    private String adminName;

    @Comment("관리자 아이디")
    @Column(nullable = false)
    private String adminId;

    @Comment("관리자 비밀번호")
    @Column(nullable = false)
    private String adminPassword;

    public void encryptPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public static Admins createAdmins(String adminName, String adminId, String adminPassword) {
        Admins admins = new Admins();
        admins.adminName = adminName;
        admins.adminId = adminId;
        admins.adminPassword = adminPassword;
        return admins;
    }
}
