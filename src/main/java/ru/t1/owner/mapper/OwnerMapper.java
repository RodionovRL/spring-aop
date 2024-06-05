package ru.t1.owner.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.t1.owner.dto.NewOwnerDto;
import ru.t1.owner.dto.OwnerDto;
import ru.t1.owner.dto.UpdateOwnerDto;
import ru.t1.owner.model.Owner;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OwnerMapper {
    OwnerDto toOwnerDto(Owner owner);

    Owner toOwner(NewOwnerDto newOwnerDto);

    @Mapping(target = "id", ignore = true)
    Owner toOwner(UpdateOwnerDto updateOwnerDto);

    List<OwnerDto> map(List<Owner> owners);
}
