package ru.t1.tracktime.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.t1.tracktime.dto.TrackTimeAverageDto;
import ru.t1.tracktime.dto.TrackTimeDto;
import ru.t1.tracktime.dto.TrackTimeStatDto;
import ru.t1.tracktime.service.TrackTimeService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/tracktime")
@RequiredArgsConstructor
public class TrackTimeController {
    private final TrackTimeService trackTimeService;

    @GetMapping()
    public List<TrackTimeDto> getAll() {
        log.info("TrackTimeController: getAll");
        return trackTimeService.getAll();
    }

    @GetMapping("/{name}")
    public List<TrackTimeDto> getAllByName(
            @PathVariable("name") String name
    ) {
        log.info("TrackTimeController: getAllByName={}", name);

        return trackTimeService.getAllByName(name);
    }

    @GetMapping("/average/{name}")
    public TrackTimeAverageDto getAverageTrackTimeByName(
            @PathVariable("name") String name
    ) {
        log.info("TrackTimeController: getAverageTrackTimeByName={}", name);
        return trackTimeService.getAverageTrackTimeByName(name);
    }

    @GetMapping("/stats/{name}")
    public TrackTimeStatDto getStatByName(
            @PathVariable("name") String name
    ) {
        log.info("TrackTimeController: getStatByName={}", name);
        return trackTimeService.getStatByName(name);
    }

}
