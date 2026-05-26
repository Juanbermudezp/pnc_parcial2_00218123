package org.example.parcial02.services;

import org.example.parcial02.domai.entities.MagicArticle;
import org.example.parcial02.domai.entities.MagicType;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface MagicArticleService {
    MagicArticle create(MagicArticle article, UUID providerId);
    MagicArticle findById(UUID id);
    MagicArticle update(UUID id, MagicArticle article, UUID providerId);
    void delete(UUID id);
    List<MagicArticle> findAll(MagicType category, UUID providerId, BigDecimal maxPrice);
}
