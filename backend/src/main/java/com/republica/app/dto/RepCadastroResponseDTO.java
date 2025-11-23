package com.republica.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RepCadastroResponseDTO {
    private Long id;
    private String tokenCadastro; 
    private String mensagem;     
}