package ru.t1.tracktime.dto;

import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TrackTimeStatDto {
    private String name;
    private Long numCalls;
    private Double avgDuration;
    private Long minDuration;
    private Long maxDuration;
}
