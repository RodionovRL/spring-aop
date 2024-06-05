package ru.t1.solution.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.aspects.annotation.TrackTime;
import ru.t1.aspects.annotation.TrackTimeAsync;
import ru.t1.exception.NotFoundException;
import ru.t1.owner.model.Owner;
import ru.t1.owner.repository.OwnerRepository;
import ru.t1.solution.dto.NewSolutionDto;
import ru.t1.solution.dto.SolutionDto;
import ru.t1.solution.dto.UpdateSolutionDto;
import ru.t1.solution.mapper.SolutionMapper;
import ru.t1.solution.model.Solution;
import ru.t1.solution.repository.SolutionRepository;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SolutionServiceImpl implements SolutionService {
    private final SolutionRepository solutionRepository;
    private final OwnerRepository ownerRepository;

    private final SolutionMapper solutionMapper;

    @Transactional()
    @Override
    @TrackTimeAsync
    public SolutionDto addSolution(NewSolutionDto newSolutionDto) {
        Solution newSolution = solutionMapper.toSolution(newSolutionDto);
        if (newSolutionDto.getOwnerId() != null) {
            Owner owner = findOwnerById(newSolutionDto.getOwnerId());
            newSolution.setOwner(owner);
        }
        Solution addedSolution = solutionRepository.save(newSolution);
        log.info("solutionService: addSolution, solution={}", addedSolution);
        return solutionMapper.toSolutionDto(addedSolution);
    }

    @Transactional(readOnly = true)
    @Override
    @TrackTime
    public SolutionDto getSolutionById(Long solutionId) {
        Solution solution = findSolutionById(solutionId);

        log.info("solutionService: getSolutionById, solutionDto={}, solutionId={}", solution, solutionId);
        return solutionMapper.toSolutionDto(solution);
    }

    @Transactional
    @Override
    @TrackTimeAsync
    public SolutionDto updateSolution(Long solutionId, UpdateSolutionDto updateSolutionDto) {
        Solution oldSolution = findSolutionById(solutionId);
        Solution newSolution = solutionMapper.toSolution(updateSolutionDto);
        if (updateSolutionDto.getOwnerId() != null) {
            Owner owner = findOwnerById(updateSolutionDto.getOwnerId());
            newSolution.setOwner(owner);
        }

        newSolution.setId(solutionId);

        if (Objects.isNull(newSolution.getName())) {
            newSolution.setName(oldSolution.getName());
        }

        if (Objects.isNull(newSolution.getVersion())) {
            newSolution.setVersion(oldSolution.getVersion());
        }

        if (Objects.isNull(newSolution.getOwner())) {
            newSolution.setOwner(oldSolution.getOwner());
        }
        Solution updatedSolution = solutionRepository.save(newSolution);
        log.info("solutionService: updateSolution, old solution={}, updatedSolution={}",
                oldSolution, updatedSolution);

        return solutionMapper.toSolutionDto(updatedSolution);
    }

    @Transactional(readOnly = true)
    @Override
    @TrackTime
    public List<SolutionDto> getAllSolutions() {
        List<Solution> allSolutions = solutionRepository.findAll();

        log.info("solutionService: getAllSolutions, num of solutions={}", allSolutions.size());
        return solutionMapper.map(allSolutions);
    }

    @Transactional
    @Override
    @TrackTimeAsync
    public void deleteSolutionById(Long solutionId) {
        findSolutionById(solutionId);

        solutionRepository.deleteById(solutionId);
        log.info("solutionService: deleteSolutionById, solutionId={}", solutionId);
    }

    @TrackTime
    private Solution findSolutionById(long solutionId) {
        return solutionRepository.findById(solutionId).orElseThrow(() ->
                new NotFoundException(String.format("solution with id=%d not found", solutionId)));
    }

    @TrackTimeAsync
    private Owner findOwnerById(long ownerId) {
        return ownerRepository.findById(ownerId).orElseThrow(() ->
                new NotFoundException(String.format("owner with id=%d not found", ownerId)));
    }
}
