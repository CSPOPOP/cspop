package kyonggi.cspop.domain.board.certification.service;

import kyonggi.cspop.domain.board.certification.CertificationBoard;
import kyonggi.cspop.domain.board.certification.dto.CertificationBoardResponseDto;
import kyonggi.cspop.domain.board.certification.repository.CertificationBoardRepository;
import kyonggi.cspop.domain.login.dto.UserSessionDto;
import kyonggi.cspop.exception.CsPopErrorCode;
import kyonggi.cspop.exception.CsPopException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CertificationBoardService {

    private final CertificationBoardRepository certificationBoardRepository;

    public List<CertificationBoard> findCertificationList() {
        return certificationBoardRepository.findAll();
    }

    public Page<CertificationBoardResponseDto> findAllCertificationBoard(Pageable pageable) {
        return certificationBoardRepository.findAllByOrderById(pageable).map(CertificationBoardResponseDto::new);
    }

    @Transactional
    public void deleteExcelListAndUploadCertificationList(List<CertificationBoard> dataList) {
        certificationBoardRepository.deleteAllInBatch();
        certificationBoardRepository.saveAll(dataList);
    }

    public String findCertificationByStudentId(String sentence, UserSessionDto userSessionDto) {
        CertificationBoard findCertification = certificationBoardRepository.findByStudentId(userSessionDto.getStudentId()).orElseThrow(() -> new CsPopException(CsPopErrorCode.CERTIFICATION_NOT_FOUND));
        String answer = "";
        switch (sentence) {
            case "전문교양":
                answer += "[" + userSessionDto.getStudentId() + " " + userSessionDto.getStudentName() + "님의 공학인증 정보]" +
                        " 전문교양 학점: " + findCertification.getProfessionalEducation();
                break;
            case "msc/bsm":
                answer += "[" + userSessionDto.getStudentId() + " " + userSessionDto.getStudentName() + "님의 공학인증 정보]" +
                        " msc/bsm 학점: " + findCertification.getMscBsm();
                break;
            case "설계학점":
                answer += "[" + userSessionDto.getStudentId() + " " + userSessionDto.getStudentName() + "님의 공학인증 정보]" +
                        " 설계 학점: " + findCertification.getDesign();
                break;
            case "전공학점":
                answer += "[" + userSessionDto.getStudentId() + " " + userSessionDto.getStudentName() + "님의 공학인증 정보]" +
                        " 전공 학점: " + findCertification.getMajor();
                break;
            case "총학점":
                answer += "[" + userSessionDto.getStudentId() + " " + userSessionDto.getStudentName() + "님의 공학인증 정보]" +
                        " 총 학점: " + findCertification.getTotal();
                break;
            case "특이사항":
                answer += "[" + userSessionDto.getStudentId() + " " + userSessionDto.getStudentName() + "님의 공학인증 정보]" +
                        " 특이사항: " + findCertification.getSpecialNote();
                break;
            case "전체공학인증":
                answer += "[" + userSessionDto.getStudentId() + " " + userSessionDto.getStudentName() + "님의 공학인증 정보]" +
                        " 전문교양 학점: " + findCertification.getProfessionalEducation() + "," +
                        " msc/bsm 학점: " + findCertification.getMscBsm() + "," +
                        " 설계 학점: " + findCertification.getDesign() + "," +
                        " 전공 학점: " + findCertification.getMajor() + "," +
                        " 총 학점: " + findCertification.getTotal() + "," +
                        " 특이사항: " + findCertification.getSpecialNote();
                break;
        }
        return answer.replace("\n", " ");
    }
}
