package com.republica.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rateios")
public class Rateio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "despesa_id", nullable = false)
    private Despesa despesa;

    @ManyToOne
    @JoinColumn(name = "morador_id", nullable = false)
    private Usuario morador;

    @Column(nullable = false)
    private BigDecimal valorDaParte;

    @Column(nullable = false)
    private boolean pago;
}