package com.republica.app.service;

import com.republica.app.dto.ConviteRequestDTO;
import com.republica.app.dto.ConviteResponseDTO;
import com.republica.app.dto.RepCadastroResponseDTO;
import com.republica.app.model.*;
import com.republica.app.model.enums.TipoMembro;
import com.republica.app.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConviteService {

    private final ConviteRepository conviteRepository;
    private final RepRepository repRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioRepRepository usuarioRepRepository;

    public ConviteResponseDTO gerarConvite(Long repId, ConviteRequestDTO dto) {
        Rep rep = repRepository.findById(repId)
                .orElseThrow(() -> new RuntimeException("República no encontrada"));

        String token = UUID.randomUUID().toString();

        Convite convite = new Convite();
        convite.setToken(token);
        convite.setRep(rep);
        convite.setTipoMembro(dto.getTipoMembro());
        convite.setLimiteUsos(dto.getLimiteUsos());
        convite.setUsosAtuais(0);
        
        convite.setDataExpiracao(LocalDateTime.now().plusHours(dto.getHorasValidade()));

        conviteRepository.save(convite);

        String linkGerado = "repapp://entrar/" + token;

        return ConviteResponseDTO.builder()
                .token(token)
                .link(linkGerado)
                .dataExpiracao(convite.getDataExpiracao().toString())
                .mensagem("Link de convite criado com sucesso!")
                .build();
    }

    @Transactional
    public RepCadastroResponseDTO entrarComConvite(String token, Long usuarioId) {
        Convite convite = conviteRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Link inválido ou não encontrado"));

        if (!convite.isValido()) {
            throw new RuntimeException("Este link de convite expirou ou atingiu seu límite de usos");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        if (convite.getTipoMembro() == TipoMembro.ADM || convite.getTipoMembro() == TipoMembro.MEMBRO) {
            boolean jaTemMoradiaPrincipal = usuarioRepRepository.existsByUsuarioIdAndTipoMembroIn(
                    usuarioId, 
                    Arrays.asList(TipoMembro.ADM, TipoMembro.MEMBRO)
            );
            
            if (jaTemMoradiaPrincipal) {
                throw new RuntimeException("Você não pode se associar como Membro/ADM porque já pertence a outra república como membro principal.");
            }
        }

        UsuarioRepId idVinculo = new UsuarioRepId(usuario.getId(), convite.getRep().getId());
        if (usuarioRepRepository.existsById(idVinculo)) {
            throw new RuntimeException("Você já está vinculado a esta república.");
        }

        UsuarioRep novoMembro = new UsuarioRep();
        novoMembro.setId(idVinculo);
        novoMembro.setUsuario(usuario);
        novoMembro.setRep(convite.getRep());
        novoMembro.setTipoMembro(convite.getTipoMembro());
        novoMembro.setDataEntradaReal(LocalDate.now());
        
        usuarioRepRepository.save(novoMembro);

        convite.setUsosAtuais(convite.getUsosAtuais() + 1);
        conviteRepository.save(convite);
        return RepCadastroResponseDTO.builder()
                .id(convite.getRep().getId())
                .mensagem("¡Bem-vindo a " + convite.getRep().getNome() + "!")
                .tokenCadastro(null)
                .build();
    }
}