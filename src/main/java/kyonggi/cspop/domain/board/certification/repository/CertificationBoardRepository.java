package kyonggi.cspop.domain.board.certification.repository;

import kyonggi.cspop.domain.board.certification.CertificationBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificationBoardRepository extends JpaRepository<CertificationBoard, Long> {
    Page<CertificationBoard> findAllByOrderById(Pageable pageable);
    Optional<CertificationBoard> findByStudentId(String studentId);

}

