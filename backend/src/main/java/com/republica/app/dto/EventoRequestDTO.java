package com.republica.app.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventoRequestDTO {
    private String titulo;
    private String descricao;
    private String local;
    private LocalDateTime dataHora;
}