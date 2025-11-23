package com.republica.app.model;

import com.republica.app.model.enums.CategoriaTarefa;
import com.republica.app.model.enums.PrioridadeTarefa;
import com.republica.app.model.enums.StatusTarefa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tarefas")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false)
    private LocalDateTime dataPrazo;

    @Column
    private LocalDateTime dataConclusao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTarefa status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrioridadeTarefa prioridade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaTarefa categoria;

    @ManyToOne
    @JoinColumn(name = "rep_id", nullable = false)
    private Rep rep;

    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private Usuario responsavel;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;
    
    @PrePersist
    protected void onCreate() {
        if (this.status == null) this.status = StatusTarefa.PENDENTE;
    }
}