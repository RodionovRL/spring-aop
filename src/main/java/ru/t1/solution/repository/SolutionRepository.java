package ru.t1.solution.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.solution.model.Solution;

public interface SolutionRepository extends JpaRepository<Solution, Long> {
}
