package ru.t1.owner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Schema(description = "DTO для обновления информации о владельце продукта")
public class UpdateOwnerDto {
    @Schema(description = "имя владельца продукта", example = "Джесси Пинкман, beatch")
    private String name;
    @Schema(description = "электронная почта владельца продукта", example = "albuqerqeForever@ya.ru")
    private String email;
}
