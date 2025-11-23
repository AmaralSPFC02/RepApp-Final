package com.republica.app.dto;

import com.republica.app.model.enums.CategoriaAviso;
import com.republica.app.model.enums.UrgenciaAviso;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class AvisoRequestDTO {
    private String titulo;
    private String descricao;
    private CategoriaAviso categoria;
    private UrgenciaAviso urgencia;
    private BigDecimal valor;
}