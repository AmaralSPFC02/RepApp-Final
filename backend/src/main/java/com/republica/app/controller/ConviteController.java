package com.republica.app.controller;

import com.republica.app.dto.ConviteRequestDTO;
import com.republica.app.dto.ConviteResponseDTO;
import com.republica.app.service.ConviteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reps/{repId}/convites")
@RequiredArgsConstructor
@Tag(name = "Convites", description = "Gerenciamento de links de convite para novos membros")
@SecurityRequirement(name = "bearerAuth")
public class ConviteController {

    private final ConviteService conviteService;

    @Operation(
        summary = "Criar Link de Convite", 
        description = "Gera um link único com limite de usos e tempo de expiração definido pelo ADM."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Convite gerado com sucesso", 
            content = @Content(schema = @Schema(implementation = ConviteResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "República não encontrada"),
        @ApiResponse(responseCode = "403", description = "Usuário não tem permissão (apenas ADM)")
    })
    @PostMapping
    public ResponseEntity<ConviteResponseDTO> criarConvite(
            @PathVariable Long repId,
            @RequestBody ConviteRequestDTO dto
    ) {
        return ResponseEntity.ok(conviteService.gerarConvite(repId, dto));
    }
}