package ru.t1.tracktime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.t1.tracktime.model.TrackTime;

import java.util.List;
import java.util.Optional;

public interface TrackTimeRepository extends JpaRepository<TrackTime, Long> {
    Optional<List<TrackTime>> findAllByName(String name);

    @Query("SELECT AVG(t.executionDuration) FROM TrackTime t WHERE t.name = :name")
    Optional<Double> findAverageTrackTimeByName(@Param("name") String name);
}
