package com.republica.app.repository;

import com.republica.app.model.Rateio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RateioRepository extends JpaRepository<Rateio, Long> {
    List<Rateio> findByDespesaRepId(Long repId);
}