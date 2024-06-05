package ru.t1.tracktime.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.t1.tracktime.dto.TrackTimeDto;
import ru.t1.tracktime.model.TrackTime;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TrackTimeMapper {
    TrackTimeDto toTrackTimeDto(TrackTime trackTime);

    List<TrackTimeDto> toList(List<TrackTime> trackTimeList);
}
