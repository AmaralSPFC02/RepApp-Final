package com.republica.app.model;

import com.republica.app.model.enums.TipoMembro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "convites")
public class Convite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne
    @JoinColumn(name = "rep_id", nullable = false)
    private Rep rep;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMembro tipoMembro;

    @Column(nullable = false)
    private Integer limiteUsos;

    @Column(nullable = false)
    private Integer usosAtuais;

    @Column(nullable = false)
    private LocalDateTime dataExpiracao;

    public boolean isValido() {
        return LocalDateTime.now().isBefore(dataExpiracao) && usosAtuais < limiteUsos;
    }
}