package ru.t1.solution.service;

import ru.t1.solution.dto.NewSolutionDto;
import ru.t1.solution.dto.SolutionDto;
import ru.t1.solution.dto.UpdateSolutionDto;

import java.util.Collection;

public interface SolutionService {
    SolutionDto addSolution(NewSolutionDto solutionDto);

    SolutionDto getSolutionById(Long id);

    SolutionDto updateSolution(Long solutionId, UpdateSolutionDto updateSolutionDto);

    Collection<SolutionDto> getAllSolutions();

    void deleteSolutionById(Long id);
}
