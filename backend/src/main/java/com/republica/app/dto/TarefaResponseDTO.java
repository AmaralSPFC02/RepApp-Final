package com.republica.app.dto;

import com.republica.app.model.enums.CategoriaTarefa;
import com.republica.app.model.enums.PrioridadeTarefa;
import com.republica.app.model.enums.StatusTarefa;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class TarefaResponseDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataPrazo;
    private LocalDateTime dataConclusao;
    private StatusTarefa status;
    private PrioridadeTarefa prioridade;
    private CategoriaTarefa categoria;
    
    private Long responsavelId;
    private String responsavelNome;
    private String responsavelFoto;
}