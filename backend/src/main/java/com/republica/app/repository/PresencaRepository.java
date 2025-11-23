package com.republica.app.repository;

import com.republica.app.model.Presenca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PresencaRepository extends JpaRepository<Presenca, Long> {
    Optional<Presenca> findByEventoIdAndUsuarioId(Long eventoId, Long usuarioId);
}