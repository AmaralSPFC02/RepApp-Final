package com.republica.app.controller;

import com.republica.app.dto.RepCadastroResponseDTO;
import com.republica.app.dto.RepRequestDTO;
import com.republica.app.model.Rep;
import com.republica.app.security.UserDetailsPrincipal;
import com.republica.app.service.RepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/reps")
@RequiredArgsConstructor
@Tag(name = "Repúblicas", description = "Gestão e criação de Repúblicas")
public class RepController {
    
    private final RepService repService;

    @Operation(
        summary = "Criar República", 
        description = "Requer Token. O usuario logueado vira no novo ADM da nova rep.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping
    public ResponseEntity<RepCadastroResponseDTO> createRep(
        @RequestBody RepRequestDTO repDTO, 
        @AuthenticationPrincipal UserDetailsPrincipal currentUser
    ) {
        Rep createdRep = repService.createRep(repDTO, currentUser.getId());
        
        RepCadastroResponseDTO response = RepCadastroResponseDTO.builder()
                .id(createdRep.getId())
                .mensagem("Validado")
                .tokenCadastro(UUID.randomUUID().toString())
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}