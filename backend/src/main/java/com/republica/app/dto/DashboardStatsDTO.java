package com.republica.app.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class DashboardStatsDTO {
    private BigDecimal saldoAtual;
    private BigDecimal totalEntradasMes;
    private BigDecimal totalSaidasMes;

    private long tarefasConcluidasSemana;
    private long despesasCriadasMes;
}