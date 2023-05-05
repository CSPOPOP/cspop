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

        // 내 졸업 요건이 얼마 남았고 어떤 것이 필요한지 에 대한 작업은
        // 학년별로 공학인증 조건이 다르므로 학년별 공학인증 조건 등록하는 페이지 및 DB 구축 후, 로직 작성해야함
        // 또는 이미 공학인증 파일의 특이사항에 기재돼있으므로, 굳이 작성 안해도 됨
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
