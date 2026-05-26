package org.example.parcial02.common.mapper;

import org.example.parcial02.domai.dto.request.CreateProviderRequest;
import org.example.parcial02.domai.dto.response.ProviderResponse;
import org.example.parcial02.domai.entities.MagicProvider;
import org.springframework.stereotype.Component;

@Component
public class MagicProviderMapper {

    public MagicProvider toEntityCreate(CreateProviderRequest request) {
        if (request == null) {
            return null;
        }
        return MagicProvider.builder()
                .name(request.getName())
                .type(request.getType())
                .active(request.getActive())
                .build();
    }

    public ProviderResponse toDto(MagicProvider provider) {
        if (provider == null) {
            return null;
        }
        return ProviderResponse.builder()
                .id(provider.getId())
                .name(provider.getName())
                .type(provider.getType())
                .active(provider.getActive())
                .build();
    }
}
