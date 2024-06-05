package ru.t1.owner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.owner.model.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
