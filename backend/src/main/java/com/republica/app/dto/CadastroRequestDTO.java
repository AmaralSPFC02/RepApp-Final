package com.republica.app.dto;

import lombok.Data;

@Data
public class CadastroRequestDTO {
    private String nomeCompleto;
    private String email;
    private String senha;
    private String curso;
    private String universidade;
    private Integer ano;
    private String fotoUrl;
}
