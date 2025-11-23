package com.republica.app.repository;

import com.republica.app.model.Permissao;
import com.republica.app.model.enums.TipoMembro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
    List<Permissao> findByRepId(Long repId);

    List<Permissao> findByRepIdAndTipoMembro(Long repId, TipoMembro tipoMembro);
}