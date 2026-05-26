package org.example.parcial02.repositories;

import org.example.parcial02.domai.entities.MagicProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface MagicProviderRepository extends JpaRepository<MagicProvider, UUID> {
    boolean existsByNameIgnoreCase(String name);
}
