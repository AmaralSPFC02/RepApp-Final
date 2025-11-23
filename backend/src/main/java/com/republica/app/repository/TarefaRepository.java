package com.republica.app.repository;

import com.republica.app.model.Tarefa;
import com.republica.app.model.enums.StatusTarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    
    List<Tarefa> findTop3ByRepIdAndStatusOrderByDataPrazoAsc(Long repId, StatusTarefa status);

    List<Tarefa> findByRepIdOrderByDataPrazoDesc(Long repId);

    List<Tarefa> findByRepIdAndStatusOrderByDataPrazoAsc(Long repId, StatusTarefa status);

    List<Tarefa> findByRepIdAndResponsavelIdAndStatusNot(Long repId, Long responsavelId, StatusTarefa statusExcluido);

    long countByRepIdAndStatusAndDataConclusaoBetween(Long repId, StatusTarefa status, LocalDateTime inicio, LocalDateTime fim);
}