package ru.t1.owner.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.t1.owner.dto.NewOwnerDto;
import ru.t1.owner.dto.OwnerDto;
import ru.t1.owner.dto.UpdateOwnerDto;
import ru.t1.owner.service.OwnerService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping(path = "/owners")
@RequiredArgsConstructor
@Tag(name = "OwnerController", description = "Owner API")
public class OwnerController {
    private final OwnerService ownerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OwnerDto addOwner(
            @RequestBody @Valid NewOwnerDto newOwnerDto
    ) {
        log.info("OwnerController: POST/addOwner body={}", newOwnerDto);
        return ownerService.addOwner(newOwnerDto);
    }

    @GetMapping("/{id}")
    public OwnerDto getOwnerById(
            @PathVariable(value = "id") Long id
    ) {
        log.info("OwnerController: GET/getOwnerByIdForAdmin id={}", id);
        return ownerService.getOwnerById(id);
    }

    @PatchMapping("/{id}")
    public OwnerDto updateOwner(
            @RequestBody @Valid UpdateOwnerDto updateOwnerDto,
            @PathVariable(value = "id") Long ownerId
    ) {
        log.info("OwnerController: PATCH/updateOwner id={}, updateBody={}", ownerId, updateOwnerDto);
        return ownerService.updateOwner(ownerId, updateOwnerDto);
    }

    @GetMapping
    public Collection<OwnerDto> getAllOwners() {
        log.info("OwnerController: GET/getAllOwners");
        return ownerService.getAllOwners();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOwnerById(
            @PathVariable("id") Long id
    ) {
        log.info("OwnerController: DELETE/deleteOwnerById id= {}", id);
        ownerService.deleteOwnerById(id);
    }
}
