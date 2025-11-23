package com.republica.app.service;

import com.republica.app.dto.AvisoRequestDTO;
import com.republica.app.dto.AvisoResponseDTO;
import com.republica.app.model.Aviso;
import com.republica.app.model.Rep;
import com.republica.app.model.Usuario;
import com.republica.app.repository.AvisoRepository;
import com.republica.app.repository.RepRepository;
import com.republica.app.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvisoService {

    private final AvisoRepository avisoRepository;
    private final RepRepository repRepository;
    private final UsuarioRepository usuarioRepository;

    public List<AvisoResponseDTO> listarAvisos(Long repId) {
        return avisoRepository.findByRepIdOrderByDataCriacaoDesc(repId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AvisoResponseDTO criarAviso(Long repId, Long autorId, AvisoRequestDTO dto) {
        Rep rep = repRepository.findById(repId)
                .orElseThrow(() -> new RuntimeException("República não encontrada"));
        
        Usuario autor = usuarioRepository.findById(autorId)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

        Aviso aviso = new Aviso();
        aviso.setTitulo(dto.getTitulo());
        aviso.setDescricao(dto.getDescricao());
        aviso.setCategoria(dto.getCategoria());
        aviso.setUrgencia(dto.getUrgencia());
        aviso.setValor(dto.getValor());
        aviso.setRep(rep);
        aviso.setAutor(autor);

        Aviso salvo = avisoRepository.save(aviso);
        return mapToDTO(salvo);
    }
    
    public void toggleStatus(Long avisoId) {
        Aviso aviso = avisoRepository.findById(avisoId)
                .orElseThrow(() -> new RuntimeException("Aviso não encontrado"));
        aviso.setAtivo(!aviso.isAtivo());
        avisoRepository.save(aviso);
    }

    private AvisoResponseDTO mapToDTO(Aviso aviso) {
        return AvisoResponseDTO.builder()
                .id(aviso.getId())
                .titulo(aviso.getTitulo())
                .descricao(aviso.getDescricao())
                .categoria(aviso.getCategoria())
                .urgencia(aviso.getUrgencia())
                .valor(aviso.getValor())
                .ativo(aviso.isAtivo())
                .autorId(aviso.getAutor().getId())
                .autorNome(aviso.getAutor().getNomeCompleto())
                .autorFoto(aviso.getAutor().getFotoUrl())
                .dataCriacao(aviso.getDataCriacao())
                .build();
    }
}