package ru.t1.owner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@Schema(description = "DTO владельца продукта")
public class OwnerDto {
    @Schema(description = "id владельца продукта", example = "1")
    private Long id;
    @Schema(description = "имя владельца продукта", example = "Джесси Пинкман")
    private String name;
    @Schema(description = "электронная почта владельца продукта", example = "albuqerke@ya.ru")
    private String email;
}
