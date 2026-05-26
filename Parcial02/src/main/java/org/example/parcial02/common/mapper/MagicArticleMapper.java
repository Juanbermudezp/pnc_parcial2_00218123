package org.example.parcial02.common.mapper;

import lombok.RequiredArgsConstructor;
import org.example.parcial02.domai.dto.request.CreateArticleRequest;
import org.example.parcial02.domai.dto.response.ArticleResponse;
import org.example.parcial02.domai.entities.MagicArticle;
import org.example.parcial02.domai.entities.MagicProvider;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MagicArticleMapper {

    private final org.example.parcial02.common.mapper.MagicProviderMapper providerMapper;

    public MagicArticle toEntityCreate(CreateArticleRequest request, MagicProvider provider) {
        if (request == null) {
            return null;
        }
        return MagicArticle.builder()
                .name(request.getName())
                .type(request.getType())
                .price(request.getPrice())
                .provider(provider)
                .build();
    }

    public ArticleResponse toDto(MagicArticle article) {
        if (article == null) {
            return null;
        }
        return ArticleResponse.builder()
                .id(article.getId())
                .name(article.getName())
                .type(article.getType())
                .price(article.getPrice())
                .provider(providerMapper.toDto(article.getProvider()))
                .build();
    }
}
