package com.republica.app.controller;

import com.republica.app.dto.JoinRequestDTO;
import com.republica.app.dto.RepCadastroResponseDTO;
import com.republica.app.security.UserDetailsPrincipal;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/convites")
@RequiredArgsConstructor
@Tag(name = "Entrar na República", description = "Acesso à república via link de convite")
public class JoinController {

    private final ConviteService conviteService;

    @Operation(
        summary = "Usar Link de Convite", 
        description = "Permite que um usuário autenticado entre em uma república usando um token válido. " +
                      "O sistema valida se o usuário já possui uma república principal antes de aceitar."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entrada realizada com sucesso", 
            content = @Content(schema = @Schema(implementation = RepCadastroResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Link expirado, inválido ou usuário já possui outra república (regra de negócio)")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/entrar")
    public ResponseEntity<RepCadastroResponseDTO> entrarComLink(
            @RequestBody JoinRequestDTO dto,
            @AuthenticationPrincipal UserDetailsPrincipal currentUser
    ) {
        return ResponseEntity.ok(conviteService.entrarComConvite(dto.getToken(), currentUser.getId()));
    }
}