package kyonggi.cspop.application.controller.board.schedule;

import kyonggi.cspop.application.controller.board.schedule.dto.ScheduleDto;
import kyonggi.cspop.application.controller.board.schedule.dto.scheduleBoarad.*;
import kyonggi.cspop.domain.board.schedule.ScheduleBoard;
import kyonggi.cspop.domain.board.schedule.service.ScheduleBoardService;
import kyonggi.cspop.domain.schedule.Schedules;
import kyonggi.cspop.domain.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/graduation")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleBoardService scheduleBoardService;

    @GetMapping("/schedule")
    public String showSchedule(Model model) {
        List<Schedules> schedules = scheduleService.findScheduleList();
        model.addAttribute("schedules", schedules);
        List<ScheduleBoard> schedulesTextList = scheduleBoardService.findScheduleBoardList();
        model.addAttribute("schedulesTextList", schedulesTextList);
        return "graduation/schedule/progress_schedule";
    }

    @PostMapping("/scheduleBoard/modify/receivedText")
    public ResponseEntity<Void> modifyReceivedText(@RequestBody ReceivedText receivedText) {
        scheduleBoardService.updateReceivedText(receivedText.getReceivedText());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/scheduleBoard/modify/proposalText")
    public ResponseEntity<Void> modifyProposalText(@RequestBody ProposalText proposalText) {
        scheduleBoardService.updateProposalText(proposalText.getProposalText());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/scheduleBoard/modify/interimReportText")
    public ResponseEntity<Void> modifyInterimReportText(@RequestBody InterimReportText interimReportText) {
        scheduleBoardService.updateInterimReportText(interimReportText.getInterimReportText());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/scheduleBoard/modify/finalReportText")
    public ResponseEntity<Void> modifyFinalReportText(@RequestBody FinalReportText finalReportText) {
        scheduleBoardService.updateFinalReportText(finalReportText.getFinalReportText());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/scheduleBoard/modify/finalPassText")
    public ResponseEntity<Void> modifyFinalPassText(@RequestBody FinalPassText finalPassText) {
        scheduleBoardService.updateFinalPassText(finalPassText.getFinalPassText());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/scheduleBoard/modify/otherQualificationsText")
    public ResponseEntity<Void> modifyOtherQualificationsText(@RequestBody OtherQualificationsText otherQualificationsText) {
        scheduleBoardService.updateOtherQualificationsText(otherQualificationsText.getOtherQualificationsText());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/schedule/modify/{id}")
    public ResponseEntity<Void> scheduleModify(@PathVariable Long id, @RequestBody ScheduleDto scheduleDto) {
        scheduleService.updateSchedules(id, scheduleDto);
        return ResponseEntity.noContent().build();
    }
}