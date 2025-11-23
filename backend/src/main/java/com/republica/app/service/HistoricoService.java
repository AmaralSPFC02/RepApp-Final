package com.republica.app.service;

import com.republica.app.dto.HistoricoResponseDTO;
import com.republica.app.model.Historico;
import com.republica.app.model.Rep;
import com.republica.app.model.Usuario;
import com.republica.app.model.enums.TipoAtividade;
import com.republica.app.repository.HistoricoRepository;
import com.republica.app.repository.RepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoricoService {

    private final HistoricoRepository historicoRepository;
    private final RepRepository repRepository;

    public void registrarAtividade(TipoAtividade tipo, String descricao, String detalhe, Usuario autor, Rep rep) {
        Historico h = new Historico();
        h.setTipo(tipo);
        h.setDescricao(descricao);
        h.setDetalheExtra(detalhe);
        h.setDataHora(LocalDateTime.now());
        h.setAutor(autor);
        h.setRep(rep);
        historicoRepository.save(h);
    }

    public List<HistoricoResponseDTO> listarRecentes(Long repId) {
        return historicoRepository.findTop10ByRepIdOrderByDataHoraDesc(repId).stream()
                .map(h -> HistoricoResponseDTO.builder()
                        .id(h.getId())
                        .tipo(h.getTipo())
                        .titulo(montarTitulo(h))
                        .descricao(h.getDescricao() + (h.getDetalheExtra() != null ? " - " + h.getDetalheExtra() : ""))
                        .dataHora(h.getDataHora())
                        .autorFoto(h.getAutor().getFotoUrl())
                        .build())
                .collect(Collectors.toList());
    }

    private String montarTitulo(Historico h) {
        String nome = h.getAutor().getNomeCompleto().split(" ")[0]; 
        switch (h.getTipo()) {
            case TAREFA_CONCLUIDA: return nome + " completou uma tarefa";
            case DESPESA_CRIADA: return nome + " adicionou uma despesa";
            case EVENTO_CRIADO: return nome + " criou um evento";
            case AVISO_POSTADO: return nome + " postou um aviso";
            default: return nome + " realizou uma ação";
        }
    }
}