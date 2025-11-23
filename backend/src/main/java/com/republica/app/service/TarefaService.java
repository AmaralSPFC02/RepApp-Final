package com.republica.app.service;

import com.republica.app.dto.TarefaRequestDTO;
import com.republica.app.dto.TarefaResponseDTO;
import com.republica.app.model.Rep;
import com.republica.app.model.Tarefa;
import com.republica.app.model.Usuario;
import com.republica.app.model.enums.StatusTarefa;
import com.republica.app.model.enums.TipoAtividade;
import com.republica.app.repository.RepRepository;
import com.republica.app.repository.TarefaRepository;
import com.republica.app.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final RepRepository repRepository;
    private final UsuarioRepository usuarioRepository;
    private final HistoricoService historicoService;

    @Transactional
    public TarefaResponseDTO criarTarefa(Long repId, Long autorId, TarefaRequestDTO dto) {
        Rep rep = repRepository.findById(repId)
                .orElseThrow(() -> new RuntimeException("República não encontrada"));
        Usuario autor = usuarioRepository.findById(autorId)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));
        
        Usuario responsavel = null;
        if (dto.getResponsavelId() != null) {
            responsavel = usuarioRepository.findById(dto.getResponsavelId())
                    .orElseThrow(() -> new RuntimeException("Responsável não encontrado"));
        }

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(dto.getTitulo());
        tarefa.setDescricao(dto.getDescricao());
        tarefa.setDataPrazo(dto.getDataPrazo());
        tarefa.setPrioridade(dto.getPrioridade());
        tarefa.setCategoria(dto.getCategoria());
        tarefa.setStatus(StatusTarefa.PENDENTE);
        tarefa.setRep(rep);
        tarefa.setAutor(autor);
        tarefa.setResponsavel(responsavel);

        return mapToDTO(tarefaRepository.save(tarefa));
    }

    public TarefaResponseDTO concluirTarefa(Long tarefaId) {
            Tarefa tarefa = tarefaRepository.findById(tarefaId)
                    .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

            tarefa.setStatus(StatusTarefa.CONCLUIDA);
            tarefa.setDataConclusao(LocalDateTime.now());
            
            Tarefa salva = tarefaRepository.save(tarefa);

            Usuario quienConcluyo = tarefa.getResponsavel() != null ? tarefa.getResponsavel() : tarefa.getAutor();
            
            historicoService.registrarAtividade(
                TipoAtividade.TAREFA_CONCLUIDA,
                tarefa.getTitulo(),
                "Concluída",
                quienConcluyo,
                tarefa.getRep()
            );

            return mapToDTO(salva);
        }

    public List<TarefaResponseDTO> listarDashboard(Long repId) {
        return tarefaRepository.findTop3ByRepIdAndStatusOrderByDataPrazoAsc(repId, StatusTarefa.PENDENTE)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<TarefaResponseDTO> listarTodas(Long repId) {
        return tarefaRepository.findByRepIdOrderByDataPrazoDesc(repId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<TarefaResponseDTO> listarPendentes(Long repId) {
        return tarefaRepository.findByRepIdAndStatusOrderByDataPrazoAsc(repId, StatusTarefa.PENDENTE)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<TarefaResponseDTO> listarConcluidas(Long repId) {
        return tarefaRepository.findByRepIdAndStatusOrderByDataPrazoAsc(repId, StatusTarefa.CONCLUIDA)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<TarefaResponseDTO> listarMinhas(Long repId, Long usuarioId) {
        return tarefaRepository.findByRepIdAndResponsavelIdAndStatusNot(repId, usuarioId, StatusTarefa.CONCLUIDA)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private TarefaResponseDTO mapToDTO(Tarefa t) {
        return TarefaResponseDTO.builder()
                .id(t.getId())
                .titulo(t.getTitulo())
                .descricao(t.getDescricao())
                .dataPrazo(t.getDataPrazo())
                .dataConclusao(t.getDataConclusao())
                .status(t.getStatus())
                .prioridade(t.getPrioridade())
                .categoria(t.getCategoria())
                .responsavelId(t.getResponsavel() != null ? t.getResponsavel().getId() : null)
                .responsavelNome(t.getResponsavel() != null ? t.getResponsavel().getNomeCompleto() : "Não atribuído")
                .responsavelFoto(t.getResponsavel() != null ? t.getResponsavel().getFotoUrl() : null)
                .build();
    }
}