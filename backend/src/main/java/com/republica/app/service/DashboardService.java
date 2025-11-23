package com.republica.app.service;

import com.republica.app.dto.DashboardStatsDTO;
import com.republica.app.model.enums.StatusTarefa;
import com.republica.app.repository.DespesaRepository;
import com.republica.app.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TarefaRepository tarefaRepository;
    private final DespesaRepository despesaRepository;

    public DashboardStatsDTO calcularEstatisticas(Long repId) {
        LocalDateTime agora = LocalDateTime.now();
        LocalDate hoje = LocalDate.now();

        LocalDateTime inicioSemana = agora.with(java.time.DayOfWeek.MONDAY).with(LocalTime.MIN);
        LocalDateTime fimSemana = agora.with(java.time.DayOfWeek.SUNDAY).with(LocalTime.MAX);

        LocalDate inicioMes = hoje.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate fimMes = hoje.with(TemporalAdjusters.lastDayOfMonth());

        long tarefasSemana = tarefaRepository.countByRepIdAndStatusAndDataConclusaoBetween(
                repId, StatusTarefa.CONCLUIDA, inicioSemana, fimSemana
        );

        long despesasMes = despesaRepository.countByRepIdAndDataDespesaBetween(
                repId, inicioMes, fimMes
        );

        BigDecimal gastosMes = despesaRepository.somarGastosNoPeriodo(repId, inicioMes, fimMes);
        
        BigDecimal saldoSimulado = BigDecimal.valueOf(1247.50);
        BigDecimal entradasSimuladas = BigDecimal.valueOf(320.00);

        return DashboardStatsDTO.builder()
                .tarefasConcluidasSemana(tarefasSemana)
                .despesasCriadasMes(despesasMes)
                .saldoAtual(saldoSimulado.subtract(gastosMes))
                .totalSaidasMes(gastosMes)
                .totalEntradasMes(entradasSimuladas)
                .build();
    }
}