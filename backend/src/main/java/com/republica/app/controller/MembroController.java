package com.republica.app.controller;

import com.republica.app.dto.MembroResponseDTO;
import com.republica.app.model.enums.TipoMembro;
import com.republica.app.service.MembroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reps/{repId}/membros")
@RequiredArgsConstructor
@Tag(name = "Gestão de Membros", description = "Listagem, alteração de permissões e remoção de moradores")
@SecurityRequirement(name = "bearerAuth")
public class MembroController {

    private final MembroService membroService;

    @Operation(summary = "Listar Membros", description = "Retorna a lista de todos os moradores da república.")
    @GetMapping
    public ResponseEntity<List<MembroResponseDTO>> listar(@PathVariable Long repId) {
        return ResponseEntity.ok(membroService.listarMembros(repId));
    }

    @Operation(summary = "Alterar Função", description = "Atualiza o papel de um morador (Ex: de MEMBRO para ADM). Requer permissão de ADM.")
    @PutMapping("/{usuarioId}/funcao")
    public ResponseEntity<Void> alterarFuncao(
            @PathVariable Long repId, 
            @PathVariable Long usuarioId, 
            @Parameter(description = "Nova função a ser atribuída (ADM, MEMBRO, AGREGADO, EX_MORADOR)") 
            @RequestParam TipoMembro novaFuncao
    ) {
        membroService.alterarFuncao(repId, usuarioId, novaFuncao);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remover Membro", description = "Remove um usuário da república (Expulsar ou Sair).")
    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> removerMembro(@PathVariable Long repId, @PathVariable Long usuarioId) {
        membroService.removerMembro(repId, usuarioId);
        return ResponseEntity.noContent().build();
    }
}