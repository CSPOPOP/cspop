package kyonggi.cspop.domain.board.schedule.service;

import kyonggi.cspop.domain.board.schedule.ScheduleBoard;
import kyonggi.cspop.domain.board.schedule.repository.ScheduleBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleBoardService {
    private final ScheduleBoardRepository scheduleBoardRepository;


    public List<ScheduleBoard> findScheduleBoardList() {
        return scheduleBoardRepository.findAll();
    }

    @Transactional
    public void updateReceivedText(String receivedText) {
        scheduleBoardRepository.findById(1l).get().updateReceivedText(receivedText);
    }

    @Transactional
    public void updateProposalText(String proposalText) {
        scheduleBoardRepository.findById(1l).get().updateProposalText(proposalText);
    }

    @Transactional
    public void updateInterimReportText(String interimReportText) {
        scheduleBoardRepository.findById(1l).get().updateInterimReportText(interimReportText);
    }

    @Transactional
    public void updateFinalReportText(String finalReportText) {
        scheduleBoardRepository.findById(1l).get().updateFinalReportText(finalReportText);
    }

    @Transactional
    public void updateFinalPassText(String finalPassText) {
        scheduleBoardRepository.findById(1l).get().updateFinalPassText(finalPassText);
    }

    @Transactional
    public void updateOtherQualificationsText(String otherQualificationsText) {
        scheduleBoardRepository.findById(1l).get().updateOtherQualificationsText(otherQualificationsText);
    }
}
