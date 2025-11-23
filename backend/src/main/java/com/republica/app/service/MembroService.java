package com.republica.app.service;

import com.republica.app.dto.MembroResponseDTO;
import com.republica.app.model.UsuarioRep;
import com.republica.app.model.UsuarioRepId;
import com.republica.app.model.enums.TipoMembro;
import com.republica.app.repository.UsuarioRepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MembroService {

    private final UsuarioRepRepository usuarioRepRepository;

    public List<MembroResponseDTO> listarMembros(Long repId) {
        List<UsuarioRep> vinculos = usuarioRepRepository.findByRepId(repId);

        return vinculos.stream().map(v -> MembroResponseDTO.builder()
                .usuarioId(v.getUsuario().getId())
                .nome(v.getUsuario().getNomeCompleto())
                .fotoUrl(v.getUsuario().getFotoUrl())
                .funcao(v.getTipoMembro())
                .apelido(v.getApelidoRep())
                .build()
        ).collect(Collectors.toList());
    }

    public void alterarFuncao(Long repId, Long usuarioId, TipoMembro novaFuncao) {
        UsuarioRepId id = new UsuarioRepId(usuarioId, repId);
        
        UsuarioRep membro = usuarioRepRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membro não encontrado nesta república"));

        membro.setTipoMembro(novaFuncao);
        usuarioRepRepository.save(membro);
    }
    
    public void removerMembro(Long repId, Long usuarioId) {
        UsuarioRepId id = new UsuarioRepId(usuarioId, repId);
        if (usuarioRepRepository.existsById(id)) {
            usuarioRepRepository.deleteById(id);
        } else {
            throw new RuntimeException("Membro não encontrado");
        }
    }
}