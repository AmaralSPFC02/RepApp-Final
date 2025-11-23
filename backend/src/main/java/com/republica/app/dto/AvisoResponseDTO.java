package com.republica.app.dto;

import com.republica.app.model.enums.CategoriaAviso;
import com.republica.app.model.enums.UrgenciaAviso;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class AvisoResponseDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private CategoriaAviso categoria;
    private UrgenciaAviso urgencia;
    private BigDecimal valor;
    private boolean ativo;
    
    private Long autorId;
    private String autorNome;
    private String autorFoto;
    
    private LocalDateTime dataCriacao;
}