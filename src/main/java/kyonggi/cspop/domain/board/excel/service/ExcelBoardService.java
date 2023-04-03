package kyonggi.cspop.domain.board.excel.service;

import kyonggi.cspop.domain.board.excel.ExcelBoard;
import kyonggi.cspop.domain.board.excel.dto.ExcelBoardResponseDto;
import kyonggi.cspop.domain.board.excel.repository.ExcelBoardRepository;
import kyonggi.cspop.domain.form.submitform.SubmitForm;
import kyonggi.cspop.domain.users.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExcelBoardService {

    private final ExcelBoardRepository excelBoardRepository;

    public List<ExcelBoard> findExcelList() {
        return excelBoardRepository.findAll();
    }

    public Page<ExcelBoardResponseDto> findAllExcelBoard(Pageable pageable) {
        return excelBoardRepository.findAllByOrderById(pageable).map(ExcelBoardResponseDto::new);
    }

    @Transactional
    public void deleteExcelListAndUploadExcelList(List<ExcelBoard> dataList) {
        excelBoardRepository.deleteAllInBatch();
        excelBoardRepository.saveAll(dataList);
    }

    public Optional<ExcelBoard> findExcelByStudentId(String studentId) {
        Optional<ExcelBoard> excelBoard = excelBoardRepository.findByStudentId(studentId);
        return excelBoard;
    }

    @Transactional
    public void addExcelBySubmitForm(Users users, SubmitForm submitForm) {
        excelBoardRepository.save(ExcelBoard.addExcelBySubmitForm(users, submitForm));
    }

    @Transactional
    public void updateExcelByProposalForm(Users users) {
        ExcelBoard excelBoard = excelBoardRepository.findByStudentId(users.getStudentId()).get();
        excelBoard.updateExcelByProposalForm();
    }

    @Transactional
    public void updateExcelByInterimForm(Users users) {
        ExcelBoard excelBoard = excelBoardRepository.findByStudentId(users.getStudentId()).get();
        excelBoard.updateExcelByInterimForm();
    }

    @Transactional
    public void updateExcelByFinalForm(Users users){
        ExcelBoard excelBoard = excelBoardRepository.findByStudentId(users.getStudentId()).get();
        excelBoard.updateExcelByFinalForm();
    }
    @Transactional
    public void updateExcelByOtherForm(Users users){
        ExcelBoard excelBoard = excelBoardRepository.findByStudentId(users.getStudentId()).get();
        excelBoard.updateExcelByOtherForm();
    }
}
