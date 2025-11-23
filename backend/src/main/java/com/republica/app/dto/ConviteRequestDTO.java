package com.republica.app.dto;

import com.republica.app.model.enums.TipoMembro;
import lombok.Data;

@Data
public class ConviteRequestDTO {
    private TipoMembro tipoMembro; 
    private Integer limiteUsos;    
    private Long horasValidade;   
}