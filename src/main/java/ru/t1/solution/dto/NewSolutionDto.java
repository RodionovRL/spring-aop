package ru.t1.solution.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO создания нового продукта")
public class NewSolutionDto {
    @Schema(description = "Наименование продукта", example = "Синий")
    @Size(min = 2, max = 30, message = "validation name size error")
    @Pattern(regexp = "^(?=.*[a-zA-Zа-яёЁА-Я\\d_\\S]).+$")
    @NotBlank
    private String name;
    @Schema(description = "Версия продукта", example = "0.1.0")
    @Builder.Default
    private String version = "0.0.1";
    @Schema (description = "id владельца продукта", example = "2")
    @Positive
    private Long ownerId;
}