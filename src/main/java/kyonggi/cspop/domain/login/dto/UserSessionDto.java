package kyonggi.cspop.domain.login.dto;

import kyonggi.cspop.domain.admins.Admins;
import kyonggi.cspop.domain.users.Users;
import lombok.Data;

@Data
public class UserSessionDto {

    private String studentId;
    private String studentName;
    private String classification;

    public UserSessionDto(Users user) {
        this.studentId = user.getStudentId();
        this.studentName = user.getStudentName();
        this.classification = user.getClassification().getClassificationToString();
    }

    public UserSessionDto(Admins admin) {
        this.studentId = admin.getAdminId();
        this.studentName = admin.getAdminName();
        this.classification = "관리자";
    }

    public UserSessionDto() {
        this.studentId = "null";
        this.studentName = "null";
        this.classification = "null";
    }

    public UserSessionDto(String id, String adminName, String classification) {
        this.studentId=id;
        this.studentName=adminName;
        this.classification = classification;
    }
}
