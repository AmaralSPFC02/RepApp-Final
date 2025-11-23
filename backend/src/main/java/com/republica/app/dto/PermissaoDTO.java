package com.republica.app.dto;

import com.republica.app.model.enums.Funcionalidade;
import com.republica.app.model.enums.TipoMembro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissaoDTO {
    private Long id;
    private TipoMembro papel; 
    private Funcionalidade funcionalidade; 
    private boolean habilitado; 
    
    private String tituloFuncionalidade;
    private String descricaoFuncionalidade;
}