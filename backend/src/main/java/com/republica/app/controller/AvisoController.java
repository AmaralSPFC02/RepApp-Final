package com.republica.app.controller;

import com.republica.app.dto.AvisoRequestDTO;
import com.republica.app.dto.AvisoResponseDTO;
import com.republica.app.security.UserDetailsPrincipal;
import com.republica.app.service.AvisoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reps/{repId}/avisos")
@RequiredArgsConstructor
@Tag(name = "Mural de Avisos", description = "Gestão de comunicados e avisos da república")
@SecurityRequirement(name = "bearerAuth")
public class AvisoController {

    private final AvisoService avisoService;

    @Operation(summary = "Listar Avisos", description = "Retorna todos os avisos cadastrados no mural, ordenados por data.")
    @GetMapping
    public ResponseEntity<List<AvisoResponseDTO>> listarAvisos(@PathVariable Long repId) {
        return ResponseEntity.ok(avisoService.listarAvisos(repId));
    }

    @Operation(summary = "Adicionar Aviso", description = "Cria um novo aviso no mural com categoria e nível de urgência.")
    @PostMapping
    public ResponseEntity<AvisoResponseDTO> criarAviso(
            @PathVariable Long repId,
            @RequestBody AvisoRequestDTO dto,
            @AuthenticationPrincipal UserDetailsPrincipal currentUser
    ) {
        return ResponseEntity.ok(avisoService.criarAviso(repId, currentUser.getId(), dto));
    }
    
    @Operation(summary = "Alterar Situação", description = "Marca um aviso como resolvido/ativo (toggle).")
    @PatchMapping("/{avisoId}/status")
    public ResponseEntity<Void> alterarStatus(@PathVariable Long repId, @PathVariable Long avisoId) {
        avisoService.toggleStatus(avisoId);
        return ResponseEntity.ok().build();
    }
}