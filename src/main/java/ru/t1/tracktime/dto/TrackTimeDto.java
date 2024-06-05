package ru.t1.tracktime.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrackTimeDto {
    private Long id;
    private String name;
    private Boolean isAsync;
    private LocalDateTime timeStamp;
    private Long executionDuration;
}
