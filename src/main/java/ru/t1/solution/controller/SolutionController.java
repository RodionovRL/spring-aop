package ru.t1.solution.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.t1.solution.dto.NewSolutionDto;
import ru.t1.solution.dto.SolutionDto;
import ru.t1.solution.dto.UpdateSolutionDto;
import ru.t1.solution.service.SolutionService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping(path = "/solutions")
@RequiredArgsConstructor
public class SolutionController {
    private final SolutionService solutionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SolutionDto addSolution(
            @RequestBody @Valid NewSolutionDto newSolutionDto
    ) {
        log.info("SolutionController: POST/addSolution body={}", newSolutionDto);
        return solutionService.addSolution(newSolutionDto);
    }

    @GetMapping("/{id}")
    public SolutionDto getSolutionById(
            @PathVariable(value = "id") Long id
    ) {
        log.info("SolutionController: GET/getSolutionByIdForAdmin id={}", id);
        return solutionService.getSolutionById(id);
    }

    @PatchMapping("/{id}")
    public SolutionDto updateSolution(
            @RequestBody @Valid UpdateSolutionDto updateSolutionDto,
            @PathVariable(value = "id") Long solutionId
    ) {
        log.info("SolutionController: PATCH/updateSolution id={}, updateBody={}", solutionId, updateSolutionDto);
        return solutionService.updateSolution(solutionId, updateSolutionDto);
    }

    @GetMapping
    public Collection<SolutionDto> getAllSolutions() {
        log.info("SolutionController: GET/getAllSolutions");
        return solutionService.getAllSolutions();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSolutionById(
            @PathVariable("id") Long id
    ) {
        log.info("SolutionController: DELETE/deleteSolutionById id= {}", id);
        solutionService.deleteSolutionById(id);
    }
}
