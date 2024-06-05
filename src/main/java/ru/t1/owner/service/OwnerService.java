package ru.t1.owner.service;

import ru.t1.owner.dto.NewOwnerDto;
import ru.t1.owner.dto.OwnerDto;
import ru.t1.owner.dto.UpdateOwnerDto;

import java.util.Collection;

public interface OwnerService {
    OwnerDto addOwner(NewOwnerDto ownerDto);

    OwnerDto getOwnerById(Long id);

    OwnerDto updateOwner(Long ownerId, UpdateOwnerDto updateOwnerDto);

    Collection<OwnerDto> getAllOwners();

    void deleteOwnerById(Long id);
}
