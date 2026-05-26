package org.example.parcial02.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.parcial02.domai.entities.MagicArticle;
import org.example.parcial02.domai.entities.MagicProvider;
import org.example.parcial02.domai.entities.MagicType;
import org.example.parcial02.exception.BusinessRuleException;
import org.example.parcial02.exception.ConflictException;
import org.example.parcial02.exception.ResourceNotFoundException;
import org.example.parcial02.repositories.MagicArticleRepository;
import org.example.parcial02.repositories.MagicProviderRepository;
import org.example.parcial02.services.MagicArticleService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MagicArticleServiceImpl implements MagicArticleService {

    private final MagicArticleRepository articleRepository;
    private final MagicProviderRepository providerRepository;

    @Override
    public MagicArticle create(MagicArticle article, UUID providerId) {
        // Regla: Nombre único
        if (articleRepository.existsByNameIgnoreCase(article.getName())) {
            throw new ConflictException("El nombre del artículo ya existe.");
        }

        // Regla: El precio debe ser mayor a cero
        if (article.getPrice() == null || article.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessRuleException("El precio del artículo debe ser estrictamente mayor a cero.");
        }

        // Regla: El proveedor debe existir
        MagicProvider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con ID: " + providerId));

        // Regla: El proveedor debe estar activo
        if (Boolean.FALSE.equals(provider.getActive())) {
            throw new BusinessRuleException("El proveedor seleccionado está inactivo.");
        }

        // Regla: La categoría (tipo) del artículo debe coincidir con el tipo del proveedor
        if (article.getType() != provider.getType()) {
            throw new BusinessRuleException("La categoría del artículo (" + article.getType() + 
                    ") no coincide con el tipo del proveedor (" + provider.getType() + ").");
        }

        article.setProvider(provider);
        return articleRepository.save(article);
    }

    @Override
    public MagicArticle findById(UUID id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artículo no encontrado con ID: " + id));
    }

    @Override
    public MagicArticle update(UUID id, MagicArticle article, UUID providerId) {
        MagicArticle existing = findById(id);

        // Regla: Nombre único (si cambia)
        if (!existing.getName().equalsIgnoreCase(article.getName()) &&
                articleRepository.existsByNameIgnoreCase(article.getName())) {
            throw new ConflictException("El nombre del artículo ya existe.");
        }

        // Regla: El precio debe ser mayor a cero
        if (article.getPrice() == null || article.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessRuleException("El precio del artículo debe ser estrictamente mayor a cero.");
        }

        // Regla: El proveedor debe existir
        MagicProvider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con ID: " + providerId));

        // Regla: El proveedor debe estar activo
        if (Boolean.FALSE.equals(provider.getActive())) {
            throw new BusinessRuleException("El proveedor seleccionado está inactivo.");
        }

        // Regla: La categoría (tipo) del artículo debe coincidir con el tipo del proveedor
        if (article.getType() != provider.getType()) {
            throw new BusinessRuleException("La categoría del artículo (" + article.getType() + 
                    ") no coincide con el tipo del proveedor (" + provider.getType() + ").");
        }

        existing.setName(article.getName());
        existing.setType(article.getType());
        existing.setPrice(article.getPrice());
        existing.setProvider(provider);

        return articleRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        MagicArticle existing = findById(id);
        articleRepository.delete(existing);
    }

    @Override
    public List<MagicArticle> findAll(MagicType category, UUID providerId, BigDecimal maxPrice) {
        return articleRepository.findAll().stream()
                .filter(a -> category == null || a.getType() == category)
                .filter(a -> providerId == null || a.getProvider().getId().equals(providerId))
                .filter(a -> maxPrice == null || a.getPrice().compareTo(maxPrice) <= 0)
                .toList();
    }
}
