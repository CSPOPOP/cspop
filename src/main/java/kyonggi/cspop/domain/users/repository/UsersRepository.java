package kyonggi.cspop.domain.users.repository;


import kyonggi.cspop.domain.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    // 회원가입, 학번으로 중복 체크
    boolean existsByStudentId(String studentId);

    Optional<Users> findByStudentId(String loginId);
}
