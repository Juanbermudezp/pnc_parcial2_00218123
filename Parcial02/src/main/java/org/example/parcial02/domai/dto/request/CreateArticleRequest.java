package org.example.parcial02.domai.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.parcial02.domai.entities.MagicType;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateArticleRequest {
    @NotBlank(message = "El nombre del artículo es requerido y no puede estar vacío")
    private String name;

    @NotNull(message = "El tipo de artículo es obligatorio")
    private MagicType type;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser estrictamente mayor a cero")
    private BigDecimal price;

    @NotNull(message = "El ID del proveedor es obligatorio")
    private UUID providerId;
}
