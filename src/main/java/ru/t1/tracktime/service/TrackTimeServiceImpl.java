package ru.t1.tracktime.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.exception.NotFoundException;
import ru.t1.tracktime.dto.TrackTimeAverageDto;
import ru.t1.tracktime.dto.TrackTimeDto;
import ru.t1.tracktime.dto.TrackTimeStatDto;
import ru.t1.tracktime.mapper.TrackTimeMapper;
import ru.t1.tracktime.model.TrackTime;
import ru.t1.tracktime.repository.TrackTimeRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackTimeServiceImpl implements TrackTimeService {

    private final TrackTimeRepository trackTimeRepository;
    private final TrackTimeMapper trackTimeMapper;

    @Async
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void add(String name, Boolean isAsync, LocalDateTime timeStamp, Long executionDuration) {
        TrackTime trackTime = new TrackTime();
        trackTime.setName(name);
        trackTime.setIsAsync(isAsync);
        trackTime.setTimeStamp(timeStamp);
        trackTime.setExecutionDuration(executionDuration);

        log.info("TrackTime: add trackTime={}", trackTime);

        TrackTime result = trackTimeRepository.save(trackTime);

        log.info("TrackTime: save trackTime={}", result);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrackTimeDto> getAll() {
        List<TrackTime> allTrackTimes = trackTimeRepository.findAll();
        log.info("TrackTimeServiceImpl: getAll, return {} items", allTrackTimes);
        return trackTimeMapper.toList(allTrackTimes);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrackTimeDto> getAllByName(String name) {
        List<TrackTime> allTrackTimesByName = getAllTrackTimesByName(name);
        log.info("TrackTimeServiceImpl: getAllByName={}, return {} items", name, allTrackTimesByName.size());
        return trackTimeMapper.toList(allTrackTimesByName);
    }

    @Override
    @Transactional(readOnly = true)
    public TrackTimeAverageDto getAverageTrackTimeByName(String name) {
        Double averageTrackTimeByName = trackTimeRepository.findAverageTrackTimeByName(name)
                .orElseThrow(() -> new NotFoundException("Records for name=" + name + " not found"));
        log.info("TrackTimeServiceImpl: getAverageTrackTimeByName={}, getAverageTrackTime={}",
                name, averageTrackTimeByName);
        return TrackTimeAverageDto.builder()
                .name(name)
                .averageDuration(averageTrackTimeByName)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public TrackTimeStatDto getStatByName(String name) {
        List<TrackTime> allTrackTimesByName = getAllTrackTimesByName(name);

        String methodName = allTrackTimesByName.stream().findFirst().orElseThrow().getName();
        Long numCalls = (long) allTrackTimesByName.size();
        Double avgDuration = allTrackTimesByName.stream()
                .mapToLong(TrackTime::getExecutionDuration)
                .average()
                .orElse(0);
        Long minDuration = allTrackTimesByName.stream()
                .mapToLong(TrackTime::getExecutionDuration)
                .min()
                .orElse(0);
        Long maxDuration = allTrackTimesByName.stream()
                .mapToLong(TrackTime::getExecutionDuration)
                .max()
                .orElse(0);

        TrackTimeStatDto trackTimeStatDto = TrackTimeStatDto.builder()
                .name(methodName)
                .numCalls(numCalls)
                .avgDuration(avgDuration)
                .minDuration(minDuration)
                .maxDuration(maxDuration)
                .build();

        log.info("TrackTimeServiceImpl: getStatByName={}, trackTimeStatDto={}",
                name, trackTimeStatDto);

        return trackTimeStatDto;
    }

    private List<TrackTime> getAllTrackTimesByName(String name) {
        return trackTimeRepository.findAllByName(name)
                .orElseThrow(() -> new NotFoundException("Records for name=" + name + " not found"));
    }
}
