package ru.t1.owner.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.aspects.annotation.TrackTime;
import ru.t1.aspects.annotation.TrackTimeAsync;
import ru.t1.exception.NotFoundException;
import ru.t1.owner.dto.NewOwnerDto;
import ru.t1.owner.dto.OwnerDto;
import ru.t1.owner.dto.UpdateOwnerDto;
import ru.t1.owner.mapper.OwnerMapper;
import ru.t1.owner.model.Owner;
import ru.t1.owner.repository.OwnerRepository;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;

    @Transactional()
    @Override
    @TrackTime
    public OwnerDto addOwner(NewOwnerDto newOwnerDto) {
        Owner newOwner = ownerMapper.toOwner(newOwnerDto);

        Owner addedOwner = ownerRepository.save(newOwner);
        log.info("ownerService: addOwner, owner={}", addedOwner);
        return ownerMapper.toOwnerDto(addedOwner);
    }

    @Transactional(readOnly = true)
    @Override
    @TrackTimeAsync
    public OwnerDto getOwnerById(Long ownerId) {
        Owner owner = findOwnerById(ownerId);

        log.info("ownerService: getOwnerById, ownerDto={}, ownerId={}", owner, ownerId);
        return ownerMapper.toOwnerDto(owner);
    }

    @Transactional
    @Override
    @TrackTime
    public OwnerDto updateOwner(Long ownerId, UpdateOwnerDto updateOwnerDto) {
        Owner oldOwner = findOwnerById(ownerId);
        Owner newOwner = ownerMapper.toOwner(updateOwnerDto);
        newOwner.setId(ownerId);

        if (Objects.isNull(newOwner.getName())) {
            newOwner.setName(oldOwner.getName());
        }

        if (Objects.isNull(newOwner.getEmail())) {
            newOwner.setEmail(oldOwner.getEmail());
        }

        Owner updatedOwner = ownerRepository.save(newOwner);
        log.info("ownerService: updateOwner, old owner={}, updatedOwner={}",
                oldOwner, updatedOwner);

        return ownerMapper.toOwnerDto(updatedOwner);
    }

    @Transactional(readOnly = true)
    @Override
    @TrackTimeAsync
    public List<OwnerDto> getAllOwners() {
        List<Owner> allOwners = ownerRepository.findAll();

        log.info("ownerService: getAllOwners, num of owners={}", allOwners.size());
        return ownerMapper.map(allOwners);
    }

    @Transactional
    @Override
    @TrackTime
    public void deleteOwnerById(Long ownerId) {
        findOwnerById(ownerId);

        ownerRepository.deleteById(ownerId);
        log.info("ownerService: deleteOwnerById, ownerId={}", ownerId);
    }

    @TrackTimeAsync
    private Owner findOwnerById(long ownerId) {
        return ownerRepository.findById(ownerId).orElseThrow(() ->
                new NotFoundException(String.format("owner with id=%d not found", ownerId)));
    }
}
