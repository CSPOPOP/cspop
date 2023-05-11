package kyonggi.cspop.domain.users.repository;


import kyonggi.cspop.domain.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    boolean existsByStudentId(String studentId);

    boolean existsUsersByAnswerPw(String answerPw);

    Optional<Users> findByStudentId(String studentId);
}
