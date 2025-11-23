package com.republica.app.dto;

import com.republica.app.model.enums.CategoriaTarefa;
import com.republica.app.model.enums.PrioridadeTarefa;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TarefaRequestDTO {
    private String titulo;
    private String descricao;
    private LocalDateTime dataPrazo;
    private PrioridadeTarefa prioridade;
    private CategoriaTarefa categoria;
    private Long responsavelId;
}