package ru.t1.solution.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Schema(description = "DTO для обновления информации о продукте")
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSolutionDto {
    @Schema(description = "Наименование продукта", example = "Синий")
    @Size(min = 2, max = 30, message = "validation name size error")
    @Pattern(regexp = "^(?=.*[a-zA-Zа-яёЁА-Я\\d_\\S]).+$")
    private String name;
    @Schema(description = "Версия продукта")
    private String version;
    @Positive
    private Long ownerId;
}