package kyonggi.cspop.domain.schedule.service;

import kyonggi.cspop.application.controller.board.schedule.dto.ScheduleDto;
import kyonggi.cspop.domain.schedule.Schedules;
import kyonggi.cspop.domain.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public List<Schedules> findScheduleList() {
        return scheduleRepository.findAll();
    }

    public Schedules findByScheduleId(Long id) {
        return scheduleRepository.findById(id).get();
    }

    @Transactional
    public void updateSchedules(Long id, ScheduleDto scheduleDto) {
        Schedules schedules = findByScheduleId(id);
        schedules.updateInfo(scheduleDto);
    }

    @Transactional
    public void autoUpdateSchedulesState() {
        List<Schedules> findScheduleList = scheduleRepository.findAll();
        findScheduleList.stream().forEach(e->e.updateScheduleState());
    }
}
