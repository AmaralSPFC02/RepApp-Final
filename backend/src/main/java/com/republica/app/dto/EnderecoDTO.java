package com.republica.app.dto;

import lombok.Data;

@Data
public class EnderecoDTO {
    
    private String cep;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private Integer numeroCasa;
    private Float latitude;
    private Float longitude;
    
}