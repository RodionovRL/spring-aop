package ru.t1.solution.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.t1.owner.mapper.OwnerMapper;
import ru.t1.solution.dto.NewSolutionDto;
import ru.t1.solution.dto.SolutionDto;
import ru.t1.solution.dto.UpdateSolutionDto;
import ru.t1.solution.model.Solution;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {OwnerMapper.class})
public interface SolutionMapper {
    SolutionDto toSolutionDto(Solution solution);

    Solution toSolution(NewSolutionDto newSolutionDto);

    @Mapping(target = "id", ignore = true)
    Solution toSolution(UpdateSolutionDto updateSolutionDto);

    List<SolutionDto> map(List<Solution> solutions);
}
