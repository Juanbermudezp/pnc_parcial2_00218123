package org.example.parcial02.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.parcial02.common.mapper.MagicArticleMapper;
import org.example.parcial02.domai.dto.request.CreateArticleRequest;
import org.example.parcial02.domai.dto.request.UpdateArticleRequest;
import org.example.parcial02.domai.dto.response.ArticleResponse;
import org.example.parcial02.domai.dto.response.GeneralResponse;
import org.example.parcial02.domai.entities.MagicArticle;
import org.example.parcial02.domai.entities.MagicType;
import org.example.parcial02.services.MagicArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/artefacts")
@RequiredArgsConstructor
public class MagicArticleController {

    private final MagicArticleService articleService;
    private final MagicArticleMapper articleMapper;

    @PostMapping
    public ResponseEntity<GeneralResponse> createArticle(@RequestBody @Valid CreateArticleRequest request) {
        // Creamos una entidad temporal
        MagicArticle articleEntity = MagicArticle.builder()
                .name(request.getName())
                .type(request.getType())
                .price(request.getPrice())
                .build();
        MagicArticle saved = articleService.create(articleEntity, request.getProviderId());
        ArticleResponse responseDto = articleMapper.toDto(saved);
        return buildResponse("Artículo creado exitosamente", HttpStatus.CREATED, responseDto);
    }

    @GetMapping
    public ResponseEntity<GeneralResponse> getAllArticles(
            @RequestParam(required = false) MagicType category,
            @RequestParam(required = false) UUID provider,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        List<ArticleResponse> list = articleService.findAll(category, provider, maxPrice).stream()
                .map(articleMapper::toDto)
                .toList();
        return buildResponse("Artículos obtenidos exitosamente", HttpStatus.OK, list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse> getArticleById(@PathVariable UUID id) {
        MagicArticle article = articleService.findById(id);
        ArticleResponse responseDto = articleMapper.toDto(article);
        return buildResponse("Artículo encontrado", HttpStatus.OK, responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponse> updateArticle(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateArticleRequest request
    ) {
        MagicArticle articleEntity = MagicArticle.builder()
                .name(request.getName())
                .type(request.getType())
                .price(request.getPrice())
                .build();
        MagicArticle updated = articleService.update(id, articleEntity, request.getProviderId());
        ArticleResponse responseDto = articleMapper.toDto(updated);
        return buildResponse("Artículo actualizado exitosamente", HttpStatus.OK, responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> deleteArticle(@PathVariable UUID id) {
        articleService.delete(id);
        return buildResponse("Artículo eliminado exitosamente", HttpStatus.OK, null);
    }

    private ResponseEntity<GeneralResponse> buildResponse(String message, HttpStatus status, Object data) {
        String uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().getPath();
        return ResponseEntity
                .status(status)
                .body(GeneralResponse.builder()
                        .message(message)
                        .uri(uri)
                        .status(status.value())
                        .time(LocalDateTime.now())
                        .data(data)
                        .build()
                );
    }
}
