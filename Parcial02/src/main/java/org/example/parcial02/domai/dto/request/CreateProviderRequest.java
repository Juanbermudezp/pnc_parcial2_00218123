package org.example.parcial02.domai.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.parcial02.domai.entities.MagicType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProviderRequest {
    @NotBlank(message = "El nombre del gremio es requerido y no puede estar vacío")
    private String name;

    @NotNull(message = "El tipo que provee es obligatorio")
    private MagicType type;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean active;
}
