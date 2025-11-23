package com.republica.app.service;

import com.republica.app.dto.*;
import com.republica.app.model.*;
import com.republica.app.model.enums.TipoAtividade;
import com.republica.app.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FinanceiroService {

    private final DespesaRepository despesaRepository;
    private final RateioRepository rateioRepository;
    private final RepRepository repRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioRepRepository usuarioRepRepository;
    private final HistoricoService historicoService;

    @Transactional
    public void adicionarDespesa(Long repId, Long pagadorId, DespesaRequestDTO dto) {
        Rep rep = repRepository.findById(repId).orElseThrow();
        Usuario pagador = usuarioRepository.findById(pagadorId).orElseThrow();

        List<Usuario> envolvidos = new ArrayList<>();
        if (dto.getEnvolvidosIds() == null || dto.getEnvolvidosIds().isEmpty()) {
            envolvidos = usuarioRepRepository.findByRepId(repId).stream()
                    .map(UsuarioRep::getUsuario)
                    .collect(Collectors.toList());
        } else {
            envolvidos = usuarioRepository.findAllById(dto.getEnvolvidosIds());
        }

        if (envolvidos.isEmpty()) throw new RuntimeException("Não há ninguém para dividir a conta");

        BigDecimal valorPorPessoa = dto.getValorTotal()
                .divide(BigDecimal.valueOf(envolvidos.size()), 2, RoundingMode.CEILING);

        Despesa despesa = new Despesa();
        despesa.setDescricao(dto.getDescricao());
        despesa.setValorTotal(dto.getValorTotal());
        despesa.setDataDespesa(dto.getData());
        despesa.setComprovanteUrl(dto.getComprovanteUrl());
        despesa.setPagador(pagador);
        despesa.setRep(rep);

        List<Rateio> rateios = new ArrayList<>();
        for (Usuario morador : envolvidos) {
            Rateio r = new Rateio();
            r.setDespesa(despesa);
            r.setMorador(morador);
            r.setValorDaParte(valorPorPessoa);
            
            boolean esElPagador = morador.getId().equals(pagador.getId());
            r.setPago(esElPagador); 
            
            rateios.add(r);
        }
        despesa.setRateios(rateios);

        despesaRepository.save(despesa);

        historicoService.registrarAtividade(
                TipoAtividade.DESPESA_CRIADA,
                dto.getDescricao(),
                "R$ " + dto.getValorTotal(),
                pagador,
                rep
        );
    }

    public FinanceiroResumoDTO obterResumo(Long repId) {
        List<Despesa> recentes = despesaRepository.findByRepIdOrderByDataDespesaDesc(repId);
        
        Map<Long, BigDecimal> balanceMap = new HashMap<>();
        
        List<UsuarioRep> membros = usuarioRepRepository.findByRepId(repId);
        for (UsuarioRep ur : membros) {
            balanceMap.put(ur.getUsuario().getId(), BigDecimal.ZERO);
        }

        List<Rateio> todosRateios = rateioRepository.findByDespesaRepId(repId);
        
        for (Rateio r : todosRateios) {
            if (r.isPago()) continue;

            Long devedorId = r.getMorador().getId();
            Long credorId = r.getDespesa().getPagador().getId();

            balanceMap.merge(devedorId, r.getValorDaParte().negate(), BigDecimal::add);
            
            balanceMap.merge(credorId, r.getValorDaParte(), BigDecimal::add);
        }

        List<SaldoMoradorDTO> saldosDTO = membros.stream().map(m -> {
            BigDecimal saldo = balanceMap.getOrDefault(m.getUsuario().getId(), BigDecimal.ZERO);
            return SaldoMoradorDTO.builder()
                    .usuarioId(m.getUsuario().getId())
                    .nome(m.getUsuario().getNomeCompleto())
                    .fotoUrl(m.getUsuario().getFotoUrl())
                    .valor(saldo)
                    .statusTexto(saldo.compareTo(BigDecimal.ZERO) >= 0 ? "Deve receber" : "Deve pagar")
                    .build();
        }).collect(Collectors.toList());

        List<DespesaDTO> despesasDTO = recentes.stream().map(d -> DespesaDTO.builder()
                .id(d.getId())
                .descricao(d.getDescricao())
                .valorTotal(d.getValorTotal())
                .data(d.getDataDespesa().toString())
                .pagadorNome(d.getPagador().getNomeCompleto())
                .valorPorPessoa(d.getRateios().isEmpty() ? BigDecimal.ZERO : d.getRateios().get(0).getValorDaParte())
                .build()
        ).collect(Collectors.toList());

        return FinanceiroResumoDTO.builder()
                .saldoTotalRep(BigDecimal.ZERO)
                .saldos(saldosDTO)
                .despesasRecentes(despesasDTO)
                .build();
    }
}