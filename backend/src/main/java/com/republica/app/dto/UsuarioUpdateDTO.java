package com.republica.app.dto;

import lombok.Data;

/**
* DTO para atualização dos dados do perfil de um usuário.
* Contém apenas os campos que podem ser modificados pelo usuário.
* Tipos Wrapper (Integer) são usados ​​para permitir valores nulos,
indicando que o campo não foi submetido para atualização.
*/
@Data
public class UsuarioUpdateDTO {

    private String nomeCompleto;

    private String fotoUrl;

    private String curso;

    private String universidade;

    private Integer ano;
    
}