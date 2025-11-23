package com.republica.app.controller;

import com.republica.app.dto.EventoRequestDTO;
import com.republica.app.dto.EventoResponseDTO;
import com.republica.app.model.enums.StatusPresenca;
import com.republica.app.security.UserDetailsPrincipal;
import com.republica.app.service.AgendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reps/{repId}/agenda")
@RequiredArgsConstructor
@Tag(name = "Agenda e Eventos", description = "Calendário compartilhado e confirmação de presença")
@SecurityRequirement(name = "bearerAuth")
public class AgendaController {

    private final AgendaService agendaService;

    @Operation(summary = "Criar Evento", description = "Agenda um novo compromisso para a república.")
    @PostMapping
    public ResponseEntity<EventoResponseDTO> criarEvento(
            @PathVariable Long repId,
            @RequestBody EventoRequestDTO dto,
            @AuthenticationPrincipal UserDetailsPrincipal currentUser
    ) {
        return ResponseEntity.ok(agendaService.criarEvento(repId, currentUser.getId(), dto));
    }

    @Operation(summary = "Próximos Eventos", description = "Lista eventos futuros a partir de hoje.")
    @GetMapping("/proximos")
    public ResponseEntity<List<EventoResponseDTO>> listarProximos(
            @PathVariable Long repId,
            @AuthenticationPrincipal UserDetailsPrincipal currentUser
    ) {
        return ResponseEntity.ok(agendaService.listarProximos(repId, currentUser.getId()));
    }

    @Operation(summary = "Calendário Mensal", description = "Lista eventos de um mês específico (para preencher a grade do calendário).")
    @GetMapping("/mes")
    public ResponseEntity<List<EventoResponseDTO>> listarPorMes(
            @PathVariable Long repId,
            @RequestParam int ano,
            @RequestParam int mes,
            @AuthenticationPrincipal UserDetailsPrincipal currentUser
    ) {
        return ResponseEntity.ok(agendaService.listarPorMes(repId, currentUser.getId(), ano, mes));
    }

    @Operation(summary = "Responder Presença (RSVP)", description = "Confirma ou recusa participação no evento.")
    @PutMapping("/{eventoId}/presenca")
    public ResponseEntity<Void> responderPresenca(
            @PathVariable Long repId,
            @PathVariable Long eventoId,
            @RequestParam StatusPresenca status,
            @AuthenticationPrincipal UserDetailsPrincipal currentUser
    ) {
        agendaService.confirmarPresenca(eventoId, currentUser.getId(), status);
        return ResponseEntity.ok().build();
    }
}