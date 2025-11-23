package com.republica.app.model;

import java.time.LocalDate;

import com.republica.app.model.enums.TipoMembro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario_rep")

public class UsuarioRep {
    
    @EmbeddedId
    private UsuarioRepId id; 

    @Enumerated(EnumType.STRING)
    @Column(name="tipo_membro", nullable = false)
    private TipoMembro tipoMembro;

    @Column(name="data_entrada_real", nullable = false)
    private LocalDate dataEntradaReal;

    @Column(name = "apelido_rep")
    private String apelidoRep;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("repId")
    @JoinColumn(name = "rep_id", nullable = false)
    private Rep rep;
}