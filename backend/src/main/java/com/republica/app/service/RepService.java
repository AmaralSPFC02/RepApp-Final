package com.republica.app.service;

import com.republica.app.model.*;
import com.republica.app.model.enums.*;
import com.republica.app.dto.*;
import com.republica.app.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate; 

@Service
@RequiredArgsConstructor
public class RepService {

    private final RepRepository repRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioRepRepository usuarioRepRepository;
    private final PermissaoService permissaoService;

    @Transactional
    public Rep createRep(RepRequestDTO repDTO, Long creatorUserId) {
        Usuario creator = usuarioRepository.findById(creatorUserId)
                .orElseThrow(() -> new RuntimeException("Usuário criador não encontrado!"));

        Endereco endereco = new Endereco();
        if (repDTO.getEndereco() != null) {
            endereco.setCep(repDTO.getEndereco().getCep());
            endereco.setRua(repDTO.getEndereco().getRua());
            endereco.setBairro(repDTO.getEndereco().getBairro());
            endereco.setCidade(repDTO.getEndereco().getCidade() != null ? repDTO.getEndereco().getCidade() : "Indefinido");
            endereco.setEstado(repDTO.getEndereco().getEstado() != null ? repDTO.getEndereco().getEstado() : "MG");
            
            endereco.setNumeroCasa(repDTO.getEndereco().getNumeroCasa() != null ? repDTO.getEndereco().getNumeroCasa() : 0);
            
            endereco.setLatitude(repDTO.getEndereco().getLatitude());
            endereco.setLongitude(repDTO.getEndereco().getLongitude());
        }

        Rep newRep = new Rep();
        newRep.setNome(repDTO.getNome());
        newRep.setEmail(repDTO.getEmail());
        newRep.setLogoUrl(repDTO.getLogoUrl());
        
        newRep.setCorPrimaria(repDTO.getCorPrimaria());
        newRep.setCorSecundaria(repDTO.getCorSecundaria());
        newRep.setTelefone(repDTO.getTelefone());


        newRep.setEndereco(endereco);
        
        newRep.setStatus(StatusRep.ATIVO);
        newRep.setVisibilidade(VisibilidadeRep.PUBLICA);
        newRep.setDataCriacao(java.time.LocalDateTime.now());

        Rep savedRep = repRepository.save(newRep);

        UsuarioRepId membershipId = new UsuarioRepId(creator.getId(), savedRep.getId());
        UsuarioRep membership = new UsuarioRep();
        membership.setId(membershipId);
        membership.setUsuario(creator);
        membership.setRep(savedRep);
        membership.setTipoMembro(TipoMembro.ADM);
        membership.setDataEntradaReal(LocalDate.now());
        
        usuarioRepRepository.save(membership);

        permissaoService.inicializarPermissoes(savedRep);
        
        return savedRep;
    }
}