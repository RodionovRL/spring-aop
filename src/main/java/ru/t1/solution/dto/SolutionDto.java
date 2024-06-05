package ru.t1.solution.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.t1.owner.dto.OwnerDto;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Schema(description = "DTO продукта")
public class SolutionDto {
    @Schema(description = "id продукта", example = "1")
    private Long id;
    @Schema(description = "имя продукта", example = "Мёд")
    private String name;
    @Schema(description = "версия продукта", example = "0.1.1")
    private String version;
    @Schema(description = "данные о владельце продукта",  example = """
                                                                       id: 1,
                                                                       name: xxcxc,
                                                                       email: sdsd@sdsd.fd
                                                                    """)
    private OwnerDto ownerDto;
}
