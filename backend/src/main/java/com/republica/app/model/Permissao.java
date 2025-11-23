package com.republica.app.model;

import com.republica.app.model.enums.Funcionalidade;
import com.republica.app.model.enums.TipoMembro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "permissoes", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"rep_id", "tipo_membro", "funcionalidade"})
})
public class Permissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rep_id", nullable = false)
    private Rep rep;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_membro", nullable = false)
    private TipoMembro tipoMembro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Funcionalidade funcionalidade;

    @Column(nullable = false)
    private boolean habilitado;
}