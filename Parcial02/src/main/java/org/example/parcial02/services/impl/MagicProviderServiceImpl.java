package org.example.parcial02.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.parcial02.domai.entities.MagicProvider;
import org.example.parcial02.exception.ConflictException;
import org.example.parcial02.exception.ResourceNotFoundException;
import org.example.parcial02.repositories.MagicArticleRepository;
import org.example.parcial02.repositories.MagicProviderRepository;
import org.example.parcial02.services.MagicProviderService;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MagicProviderServiceImpl implements MagicProviderService {

    private final MagicProviderRepository providerRepository;
    private final MagicArticleRepository articleRepository;

    @Override
    public MagicProvider create(MagicProvider provider) {
        if (providerRepository.existsByNameIgnoreCase(provider.getName())) {
            throw new ConflictException("El nombre del gremio ya existe.");
        }
        return providerRepository.save(provider);
    }

    @Override
    public MagicProvider findById(UUID id) {
        return providerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con ID: " + id));
    }

    @Override
    public MagicProvider update(UUID id, MagicProvider provider) {
        MagicProvider existing = findById(id);

        if (!existing.getName().equalsIgnoreCase(provider.getName()) &&
                providerRepository.existsByNameIgnoreCase(provider.getName())) {
            throw new ConflictException("El nombre del gremio ya existe.");
        }

        existing.setName(provider.getName());
        existing.setType(provider.getType());
        existing.setActive(provider.getActive());

        return providerRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        MagicProvider existing = findById(id);

        if (articleRepository.existsByProviderId(id)) {
            throw new ConflictException("No se puede eliminar un proveedor que tenga al menos un artículo asociado.");
        }

        providerRepository.delete(existing);
    }
}
