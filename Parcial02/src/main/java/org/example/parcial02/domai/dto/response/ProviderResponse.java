package org.example.parcial02.domai.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.parcial02.domai.entities.MagicType;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProviderResponse {
    private UUID id;
    private String name;
    private MagicType type;
    private Boolean active;
}
