package kyonggi.cspop.domain.board.excel.repository;

import kyonggi.cspop.domain.board.excel.ExcelBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExcelBoardRepository extends JpaRepository<ExcelBoard,Long> {

    Page<ExcelBoard> findAllByOrderById(Pageable pageable);
    Optional<ExcelBoard> findByStudentId(String studentId);

    Page<ExcelBoard> findAllByStepOrderById(String step, Pageable pageable);

    Page<ExcelBoard> findByStudentNameContainingOrderById(String word, Pageable pageable);
    Page<ExcelBoard> findByStudentNameContainingAndStepOrderById(String studentName,String step, Pageable pageable);
    List<ExcelBoard> findExcelBoardByStep(String step);
}

