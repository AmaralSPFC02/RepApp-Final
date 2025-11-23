package com.republica.app.controller;

import com.republica.app.dto.PermissaoDTO;
import com.republica.app.service.PermissaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reps/{repId}/configuracoes")
@RequiredArgsConstructor
@Tag(name = "Configurações", description = "Gestão de permissões e regras da república")
@SecurityRequirement(name = "bearerAuth")
public class ConfiguracaoController {

    private final PermissaoService permissaoService;

    @Operation(summary = "Listar Permissões", description = "Retorna a matriz de configurações para a tela de Configurações de Acesso.")
    @GetMapping("/permissoes")
    public ResponseEntity<List<PermissaoDTO>> listarPermissoes(@PathVariable Long repId) {
        return ResponseEntity.ok(permissaoService.listarPermissoes(repId));
    }

    @Operation(summary = "Salvar Permissões", description = "Atualiza as regras de acesso para Membros e Admins.")
    @PutMapping("/permissoes")
    public ResponseEntity<Void> salvarPermissoes(
            @PathVariable Long repId,
            @RequestBody List<PermissaoDTO> novasPermissoes
    ) {
        permissaoService.atualizarPermissoes(repId, novasPermissoes);
        return ResponseEntity.ok().build();
    }
}