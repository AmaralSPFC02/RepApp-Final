package com.republica.app.controller;

import com.republica.app.dto.DashboardStatsDTO;
import com.republica.app.dto.HistoricoResponseDTO;
import com.republica.app.service.DashboardService; // Inyectar el nuevo servicio
import com.republica.app.service.HistoricoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reps/{repId}/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "Dados agregados para a tela inicial")
@SecurityRequirement(name = "bearerAuth")
public class DashboardController {

    private final HistoricoService historicoService;
    private final DashboardService dashboardService;

    @Operation(summary = "Atividade Recente", description = "Lista as últimas ações da república.")
    @GetMapping("/atividades")
    public ResponseEntity<List<HistoricoResponseDTO>> getAtividadesRecentes(@PathVariable Long repId) {
        return ResponseEntity.ok(historicoService.listarRecentes(repId));
    }

    @Operation(summary = "Estatísticas (Cards)", description = "Retorna contadores de tarefas, despesas e saldos para os cards do dashboard.")
    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getEstatisticas(@PathVariable Long repId) {
        return ResponseEntity.ok(dashboardService.calcularEstatisticas(repId));
    }
}