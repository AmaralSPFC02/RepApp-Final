package com.republica.app.dto;

import com.republica.app.model.enums.TipoAtividade;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class HistoricoResponseDTO {
    private Long id;
    private TipoAtividade tipo;
    private String titulo;       
    private String descricao;    
    private LocalDateTime dataHora;
    private String tempoDecorrido;
    private String autorFoto;    
}