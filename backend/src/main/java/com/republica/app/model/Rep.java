package com.republica.app.model;
import java.time.LocalDateTime;

import com.republica.app.model.enums.StatusRep;
import com.republica.app.model.enums.VisibilidadeRep;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "republica")

public class Rep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column
    private String logoUrl;

    @Column
    private String corPrimaria;

    @Column
    private String corSecundaria;

    @Column(unique = true)
    private String email;

    private String telefone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VisibilidadeRep visibilidade;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusRep status;

    @Column(name = "data_solicitacao_delecao")
    private LocalDateTime dataSolicitacaoDelecao;

    @Column(nullable = false,name = "data_criacao")
    private LocalDateTime dataCriacao;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;
}
