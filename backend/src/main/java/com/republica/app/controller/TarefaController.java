package com.republica.app.controller;

import com.republica.app.dto.TarefaRequestDTO;
import com.republica.app.dto.TarefaResponseDTO;
import com.republica.app.security.UserDetailsPrincipal;
import com.republica.app.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reps/{repId}/tarefas")
@RequiredArgsConstructor
@Tag(name = "Tarefas", description = "Gestão de tarefas domésticas (Limpeza, etc)")
@SecurityRequirement(name = "bearerAuth")
public class TarefaController {

    private final TarefaService tarefaService;

    @Operation(summary = "Criar Tarefa")
    @PostMapping
    public ResponseEntity<TarefaResponseDTO> criarTarefa(
            @PathVariable Long repId,
            @RequestBody TarefaRequestDTO dto,
            @AuthenticationPrincipal UserDetailsPrincipal currentUser
    ) {
        return ResponseEntity.ok(tarefaService.criarTarefa(repId, currentUser.getId(), dto));
    }

    @Operation(summary = "Concluir Tarefa (Checkbox)")
    @PatchMapping("/{tarefaId}/concluir")
    public ResponseEntity<TarefaResponseDTO> concluirTarefa(@PathVariable Long repId, @PathVariable Long tarefaId) {
        return ResponseEntity.ok(tarefaService.concluirTarefa(tarefaId));
    }

    // --- ENDPOINTS PARA LAS PESTAÑAS DEL FRONT ---

    @Operation(summary = "Tab: Dashboard (Só 3 Próximas)")
    @GetMapping("/dashboard")
    public ResponseEntity<List<TarefaResponseDTO>> listarDashboard(@PathVariable Long repId) {
        return ResponseEntity.ok(tarefaService.listarDashboard(repId));
    }

    @Operation(summary = "Tab: Todas")
    @GetMapping("/todas")
    public ResponseEntity<List<TarefaResponseDTO>> listarTodas(@PathVariable Long repId) {
        return ResponseEntity.ok(tarefaService.listarTodas(repId));
    }

    @Operation(summary = "Tab: Pendentes")
    @GetMapping("/pendentes")
    public ResponseEntity<List<TarefaResponseDTO>> listarPendentes(@PathVariable Long repId) {
        return ResponseEntity.ok(tarefaService.listarPendentes(repId));
    }

    @Operation(summary = "Tab: Concluídas")
    @GetMapping("/concluidas")
    public ResponseEntity<List<TarefaResponseDTO>> listarConcluidas(@PathVariable Long repId) {
        return ResponseEntity.ok(tarefaService.listarConcluidas(repId));
    }

    @Operation(summary = "Tab: Minhas")
    @GetMapping("/minhas")
    public ResponseEntity<List<TarefaResponseDTO>> listarMinhas(
            @PathVariable Long repId,
            @AuthenticationPrincipal UserDetailsPrincipal currentUser
    ) {
        return ResponseEntity.ok(tarefaService.listarMinhas(repId, currentUser.getId()));
    }
}