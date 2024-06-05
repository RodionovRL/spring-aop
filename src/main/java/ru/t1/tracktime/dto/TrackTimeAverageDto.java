package ru.t1.tracktime.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackTimeAverageDto {
    private String name;
    private Double averageDuration;
}
