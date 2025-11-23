package com.republica.app.service;

import com.republica.app.dto.EventoRequestDTO;
import com.republica.app.dto.EventoResponseDTO;
import com.republica.app.model.*;
import com.republica.app.model.enums.StatusPresenca;
import com.republica.app.model.enums.TipoAtividade;
import com.republica.app.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgendaService {

    private final EventoRepository eventoRepository;
    private final PresencaRepository presencaRepository;
    private final RepRepository repRepository;
    private final UsuarioRepository usuarioRepository;
    private final HistoricoService historicoService;

    @Transactional
    public EventoResponseDTO criarEvento(Long repId, Long criadorId, EventoRequestDTO dto) {
        Rep rep = repRepository.findById(repId).orElseThrow();
        Usuario criador = usuarioRepository.findById(criadorId).orElseThrow();

        Evento evento = new Evento();
        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setLocal(dto.getLocal());
        evento.setDataHora(dto.getDataHora());
        evento.setRep(rep);
        evento.setCriador(criador);

        Evento salvo = eventoRepository.save(evento);

        confirmarPresenca(salvo.getId(), criadorId, StatusPresenca.CONFIRMADO);

        historicoService.registrarAtividade(
                TipoAtividade.EVENTO_CRIADO,
                "Novo Evento: " + dto.getTitulo(),
                dto.getDataHora().toString(),
                criador,
                rep
        );

        return mapToDTO(salvo, criadorId);
    }

    public List<EventoResponseDTO> listarProximos(Long repId, Long usuarioId) {
        return eventoRepository.findByRepIdAndDataHoraAfterOrderByDataHoraAsc(repId, LocalDateTime.now())
                .stream().map(e -> mapToDTO(e, usuarioId)).collect(Collectors.toList());
    }

    public List<EventoResponseDTO> listarPorMes(Long repId, Long usuarioId, int ano, int mes) {
        YearMonth yearMonth = YearMonth.of(ano, mes);
        LocalDateTime inicio = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime fim = yearMonth.atEndOfMonth().atTime(23, 59, 59);

        return eventoRepository.findByMes(repId, inicio, fim)
                .stream().map(e -> mapToDTO(e, usuarioId)).collect(Collectors.toList());
    }

    @Transactional
    public void confirmarPresenca(Long eventoId, Long usuarioId, StatusPresenca status) {
        Evento evento = eventoRepository.findById(eventoId).orElseThrow();
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow();

        Presenca presenca = presencaRepository.findByEventoIdAndUsuarioId(eventoId, usuarioId)
                .orElse(new Presenca());

        if (presenca.getId() == null) {
            presenca.setEvento(evento);
            presenca.setUsuario(usuario);
        }
        presenca.setStatus(status);
        presencaRepository.save(presenca);
    }

    private EventoResponseDTO mapToDTO(Evento e, Long usuarioId) {
        int confirmados = e.getListaPresenca() == null ? 0 :
                (int) e.getListaPresenca().stream()
                        .filter(p -> p.getStatus() == StatusPresenca.CONFIRMADO)
                        .count();

        StatusPresenca meuStatus = StatusPresenca.PENDENTE;
        if (e.getListaPresenca() != null) {
            Optional<Presenca> minhaPresenca = e.getListaPresenca().stream()
                    .filter(p -> p.getUsuario().getId().equals(usuarioId))
                    .findFirst();
            if (minhaPresenca.isPresent()) {
                meuStatus = minhaPresenca.get().getStatus();
            }
        }

        return EventoResponseDTO.builder()
                .id(e.getId())
                .titulo(e.getTitulo())
                .descricao(e.getDescricao())
                .local(e.getLocal())
                .dataHora(e.getDataHora())
                .criadorNome(e.getCriador().getNomeCompleto())
                .totalConfirmados(confirmados)
                .meuStatus(meuStatus)
                .build();
    }
}