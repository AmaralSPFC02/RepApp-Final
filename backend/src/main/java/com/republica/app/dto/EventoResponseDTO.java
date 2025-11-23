package com.republica.app.dto;

import com.republica.app.model.enums.StatusPresenca;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class EventoResponseDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String local;
    private LocalDateTime dataHora;
    private String criadorNome;
    
    private int totalConfirmados;
    private StatusPresenca meuStatus;
}