package ru.t1.tracktime.service;

import ru.t1.tracktime.dto.TrackTimeAverageDto;
import ru.t1.tracktime.dto.TrackTimeDto;
import ru.t1.tracktime.dto.TrackTimeStatDto;

import java.time.LocalDateTime;
import java.util.List;

public interface TrackTimeService {
    /**
     * Добавление новой записи о времени выполнения метода
     *
     * @param name      имя метода
     * @param isAsync   синхронный или асинхронный
     * @param timeStamp штамп времени выполнения
     * @param executionDuration время, затраченное методом
     */
    void add(String name, Boolean isAsync, LocalDateTime timeStamp, Long executionDuration);

    List<TrackTimeDto> getAll();

    List<TrackTimeDto> getAllByName(String name);

    TrackTimeAverageDto getAverageTrackTimeByName(String name);

    TrackTimeStatDto getStatByName(String name);
}
