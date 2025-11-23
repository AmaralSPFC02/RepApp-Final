package com.republica.app.repository;

import com.republica.app.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    List<Despesa> findByRepIdOrderByDataDespesaDesc(Long repId);

    long countByRepIdAndDataDespesaBetween(Long repId, LocalDate inicio, LocalDate fim);

    @Query("SELECT COALESCE(SUM(d.valorTotal), 0) FROM Despesa d WHERE d.rep.id = :repId AND d.dataDespesa BETWEEN :inicio AND :fim")
    BigDecimal somarGastosNoPeriodo(@Param("repId") Long repId, @Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
}