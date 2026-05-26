package org.example.parcial02.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.parcial02.common.mapper.MagicProviderMapper;
import org.example.parcial02.domai.dto.request.CreateProviderRequest;
import org.example.parcial02.domai.dto.response.GeneralResponse;
import org.example.parcial02.domai.dto.response.ProviderResponse;
import org.example.parcial02.domai.entities.MagicProvider;
import org.example.parcial02.services.MagicProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/providers")
@RequiredArgsConstructor
public class MagicProviderController {

    private final MagicProviderService providerService;
    private final MagicProviderMapper providerMapper;

    @PostMapping
    public ResponseEntity<GeneralResponse> createProvider(@RequestBody @Valid CreateProviderRequest request) {
        MagicProvider entity = providerMapper.toEntityCreate(request);
        MagicProvider saved = providerService.create(entity);
        ProviderResponse responseDto = providerMapper.toDto(saved);
        return buildResponse("Proveedor creado exitosamente", HttpStatus.CREATED, responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse> getProviderById(@PathVariable UUID id) {
        MagicProvider provider = providerService.findById(id);
        ProviderResponse responseDto = providerMapper.toDto(provider);
        return buildResponse("Proveedor encontrado", HttpStatus.OK, responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponse> updateProvider(
            @PathVariable UUID id,
            @RequestBody @Valid CreateProviderRequest request
    ) {
        MagicProvider entity = providerMapper.toEntityCreate(request);
        MagicProvider updated = providerService.update(id, entity);
        ProviderResponse responseDto = providerMapper.toDto(updated);
        return buildResponse("Proveedor actualizado exitosamente", HttpStatus.OK, responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> deleteProvider(@PathVariable UUID id) {
        providerService.delete(id);
        return buildResponse("Proveedor eliminado exitosamente", HttpStatus.OK, null);
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
