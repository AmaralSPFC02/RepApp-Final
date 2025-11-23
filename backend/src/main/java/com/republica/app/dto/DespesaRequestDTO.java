package com.republica.app.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class DespesaRequestDTO {
    private String descricao;
    private BigDecimal valorTotal;
    private LocalDate data;
    private String comprovanteUrl;
    
    private List<Long> envolvidosIds; 
}