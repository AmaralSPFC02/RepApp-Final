package com.republica.app.dto;

import com.republica.app.model.enums.TipoMembro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MembroResponseDTO {
    private Long usuarioId;
    private String nome;
    private String fotoUrl;
    private TipoMembro funcao;
    private String apelido;   
}