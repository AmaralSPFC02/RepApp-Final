package com.republica.app.controller;

import com.republica.app.dto.DespesaRequestDTO;
import com.republica.app.dto.FinanceiroResumoDTO;
import com.republica.app.security.UserDetailsPrincipal;
import com.republica.app.service.FinanceiroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reps/{repId}/financeiro")
@RequiredArgsConstructor
@Tag(name = "Módulo Financeiro", description = "Gestão de despesas e rateio de contas")
@SecurityRequirement(name = "bearerAuth")
public class FinanceiroController {

    private final FinanceiroService financeiroService;

    @Operation(summary = "Resumo Financeiro", description = "Retorna o saldo de cada morador e a lista de despesas recentes.")
    @GetMapping
    public ResponseEntity<FinanceiroResumoDTO> obterResumo(@PathVariable Long repId) {
        return ResponseEntity.ok(financeiroService.obterResumo(repId));
    }

    @Operation(summary = "Adicionar Despesa", description = "Cadastra uma conta paga por um morador e divide o valor entre todos (ou selecionados).")
    @PostMapping("/despesas")
    public ResponseEntity<Void> adicionarDespesa(
            @PathVariable Long repId,
            @RequestBody DespesaRequestDTO dto,
            @AuthenticationPrincipal UserDetailsPrincipal currentUser
    ) {
        financeiroService.adicionarDespesa(repId, currentUser.getId(), dto);
        return ResponseEntity.ok().build();
    }
}