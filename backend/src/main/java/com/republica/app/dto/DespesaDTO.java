package com.republica.app.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class DespesaDTO {
    private Long id;
    private String descricao;
    private BigDecimal valorTotal;
    private String data;
    private String pagadorNome;
    private BigDecimal valorPorPessoa;
}
