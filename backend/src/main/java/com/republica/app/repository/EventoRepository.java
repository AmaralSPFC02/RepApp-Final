package com.republica.app.repository;

import com.republica.app.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    
    List<Evento> findByRepIdAndDataHoraAfterOrderByDataHoraAsc(Long repId, LocalDateTime data);

    @Query("SELECT e FROM Evento e WHERE e.rep.id = :repId AND e.dataHora BETWEEN :inicio AND :fim ORDER BY e.dataHora ASC")
    List<Evento> findByMes(@Param("repId") Long repId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}