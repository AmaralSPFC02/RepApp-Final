package com.republica.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConviteResponseDTO {
    private String link;         
    private String token;       
    private String dataExpiracao;
    private String mensagem;
}