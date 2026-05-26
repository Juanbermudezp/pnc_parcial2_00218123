package org.example.parcial02.services;

import org.example.parcial02.domai.entities.MagicProvider;
import java.util.UUID;

public interface MagicProviderService {
    MagicProvider create(MagicProvider provider);
    MagicProvider findById(UUID id);
    MagicProvider update(UUID id, MagicProvider provider);
    void delete(UUID id);
}
