package com.republica.app.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class SaldoMoradorDTO {
    private Long usuarioId;
    private String nome;
    private String fotoUrl;
    private BigDecimal valor; 
    private String statusTexto;
}