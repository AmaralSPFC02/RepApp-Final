package com.republica.app.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class FinanceiroResumoDTO {
    private BigDecimal saldoTotalRep; 
    private List<SaldoMoradorDTO> saldos;
    private List<DespesaDTO> despesasRecentes;
}