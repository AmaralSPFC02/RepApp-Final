package com.republica.app.dto;

import com.republica.app.model.enums.VisibilidadeRep;

import lombok.Data;


@Data

public class RepRequestDTO {
    private String nome;
    private String logoUrl;
    private String corPrimaria;
    private String corSecundaria;
    String cnpj;
    String email;
    String telefone;
    private VisibilidadeRep visibilidade; 

    private EnderecoDTO endereco;
}
