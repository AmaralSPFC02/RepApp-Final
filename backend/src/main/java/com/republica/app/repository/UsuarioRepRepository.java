package com.republica.app.repository;

import com.republica.app.model.UsuarioRep;
import com.republica.app.model.UsuarioRepId;
import com.republica.app.model.enums.TipoMembro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UsuarioRepRepository extends JpaRepository<UsuarioRep, UsuarioRepId> {
    List<UsuarioRep> findByUsuarioId(Long usuarioId);
    List<UsuarioRep> findByRepId(Long repId);

    @Query("SELECT count(ur) > 0 FROM UsuarioRep ur WHERE ur.usuario.id = :usuarioId AND ur.tipoMembro IN :tipos")
    boolean existsByUsuarioIdAndTipoMembroIn(@Param("usuarioId") Long usuarioId, @Param("tipos") List<TipoMembro> tipos);
}