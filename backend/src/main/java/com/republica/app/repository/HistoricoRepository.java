package com.republica.app.repository;

import com.republica.app.model.Historico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HistoricoRepository extends JpaRepository<Historico, Long> {
    List<Historico> findTop10ByRepIdOrderByDataHoraDesc(Long repId);
}