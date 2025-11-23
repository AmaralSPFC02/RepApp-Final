package com.republica.app.repository;

import com.republica.app.model.Aviso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AvisoRepository extends JpaRepository<Aviso, Long> {
    List<Aviso> findByRepIdOrderByDataCriacaoDesc(Long repId);
}