package ru.t1.tracktime.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.t1.tracktime.dto.TrackTimeAverageDto;
import ru.t1.tracktime.dto.TrackTimeDto;
import ru.t1.tracktime.dto.TrackTimeStatDto;
import ru.t1.tracktime.service.TrackTimeService;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TrackTimeController.class)
class TrackTimeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TrackTimeService trackTimeService;

    Long trackTimeId = 3L;
    String trackTimeName = "trackTimeName";
    Boolean trackTimeIsAsync = true;
    LocalDateTime trackTimeTimeStamp = LocalDateTime.now();
    Long trackTimeExecutionDuration = 16L;
    Double trackAvgDuration = 1.234;

    TrackTimeDto trackTimeDto = new TrackTimeDto(trackTimeId, trackTimeName, trackTimeIsAsync, trackTimeTimeStamp,
            trackTimeExecutionDuration);

    TrackTimeAverageDto trackTimeAverageDto = new TrackTimeAverageDto(trackTimeName, trackAvgDuration);

    Long numCalls = 4L;
    Double avgDuration =  12.3;
    Long maxDuration = 16L;

    TrackTimeStatDto trackTimeStatDto =
            new TrackTimeStatDto(trackTimeName, numCalls, avgDuration, maxDuration, maxDuration);

    @Test
    @SneakyThrows
    void getAll() {
        when(trackTimeService.getAll()).thenReturn(List.of(trackTimeDto));

        mockMvc.perform(get("/tracktime")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(trackTimeDto.getId()), Long.class))
                .andExpect(jsonPath("$.[0].name", is(trackTimeDto.getName())))
                .andExpect(jsonPath("$.[0].isAsync", is(trackTimeDto.getIsAsync())))
                .andExpect(jsonPath("$.[0].timeStamp").isNotEmpty())
                .andExpect(jsonPath("$.[0].executionDuration").value(trackTimeDto.getExecutionDuration()));
    }

    @Test
    @SneakyThrows
    void getAllByName() {
        when(trackTimeService.getAllByName(trackTimeName)).thenReturn(List.of(trackTimeDto));

        mockMvc.perform(get("/tracktime/{name}", trackTimeName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(trackTimeDto.getId()), Long.class))
                .andExpect(jsonPath("$.[0].name", is(trackTimeDto.getName())))
                .andExpect(jsonPath("$.[0].isAsync", is(trackTimeDto.getIsAsync())))
                .andExpect(jsonPath("$.[0].timeStamp").isNotEmpty())
                .andExpect(jsonPath("$.[0].executionDuration").value(trackTimeDto.getExecutionDuration()));
    }

    @Test
    @SneakyThrows
    void getAverageTrackTimeByName() {
        when(trackTimeService.getAverageTrackTimeByName(trackTimeName)).thenReturn(trackTimeAverageDto);

        mockMvc.perform(get("/tracktime/average/{name}", trackTimeName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(trackTimeAverageDto.getName())))
                .andExpect(jsonPath("$.averageDuration", is(trackTimeAverageDto.getAverageDuration())));
    }

    @Test
    @SneakyThrows
    void getStatByName() {
        when(trackTimeService.getStatByName(trackTimeName)).thenReturn(trackTimeStatDto);

        mockMvc.perform(get("/tracktime/stats/{name}", trackTimeName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(trackTimeStatDto.getName())))
                .andExpect(jsonPath("$.numCalls").value(trackTimeStatDto.getNumCalls()))
                .andExpect(jsonPath("$.avgDuration", is(trackTimeStatDto.getAvgDuration())))
                .andExpect(jsonPath("$.minDuration").value(trackTimeStatDto.getMinDuration()))
                .andExpect(jsonPath("$.maxDuration").value(trackTimeStatDto.getMaxDuration()));
    }
}