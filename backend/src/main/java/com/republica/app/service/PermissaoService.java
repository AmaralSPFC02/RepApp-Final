package com.republica.app.service;

import com.republica.app.dto.PermissaoDTO;
import com.republica.app.model.Permissao;
import com.republica.app.model.Rep;
import com.republica.app.model.enums.Funcionalidade;
import com.republica.app.model.enums.TipoMembro;
import com.republica.app.repository.PermissaoRepository;
import com.republica.app.repository.RepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissaoService {

    private final PermissaoRepository permissaoRepository;
    private final RepRepository repRepository;

    @Transactional
    public void inicializarPermissoes(Rep rep) {
        List<Permissao> defaults = new ArrayList<>();

        for (Funcionalidade f : Funcionalidade.values()) {
            defaults.add(new Permissao(null, rep, TipoMembro.ADM, f, true));
        }

        defaults.add(new Permissao(null, rep, TipoMembro.MEMBRO, Funcionalidade.CRIAR_EVENTOS, true));
        defaults.add(new Permissao(null, rep, TipoMembro.MEMBRO, Funcionalidade.VISUALIZAR_FINANCAS, true));
        defaults.add(new Permissao(null, rep, TipoMembro.MEMBRO, Funcionalidade.ENVIAR_MENSAGENS, true));
        defaults.add(new Permissao(null, rep, TipoMembro.MEMBRO, Funcionalidade.GERENCIAR_FINANCAS, false));
        defaults.add(new Permissao(null, rep, TipoMembro.MEMBRO, Funcionalidade.GERENCIAR_MEMBROS, false));
        defaults.add(new Permissao(null, rep, TipoMembro.MEMBRO, Funcionalidade.EDITAR_REPUBLICA, false));
        defaults.add(new Permissao(null, rep, TipoMembro.MEMBRO, Funcionalidade.GERENCIAR_PERMISSOES, false));
        defaults.add(new Permissao(null, rep, TipoMembro.MEMBRO, Funcionalidade.GERENCIAR_EVENTOS_OUTROS, false));
        defaults.add(new Permissao(null, rep, TipoMembro.MEMBRO, Funcionalidade.EDITAR_DADOS_REP, false));

        permissaoRepository.saveAll(defaults);
    }

    public List<PermissaoDTO> listarPermissoes(Long repId) {
        return permissaoRepository.findByRepId(repId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void atualizarPermissoes(Long repId, List<PermissaoDTO> dtos) {
        Rep rep = repRepository.findById(repId).orElseThrow();

        for (PermissaoDTO dto : dtos) {
            Optional<Permissao> existente = permissaoRepository.findAll().stream()
                    .filter(p -> p.getRep().getId().equals(repId) && 
                                 p.getTipoMembro() == dto.getPapel() && 
                                 p.getFuncionalidade() == dto.getFuncionalidade())
                    .findFirst();

            if (existente.isPresent()) {
                Permissao p = existente.get();
                p.setHabilitado(dto.isHabilitado());
                permissaoRepository.save(p);
            }
        }
    }

    public boolean temPermissao(Long repId, TipoMembro papelDelUsuario, Funcionalidade accion) {
        if (papelDelUsuario == TipoMembro.ADM) return true;

        return permissaoRepository.findByRepIdAndTipoMembro(repId, papelDelUsuario).stream()
                .filter(p -> p.getFuncionalidade() == accion)
                .findFirst()
                .map(Permissao::isHabilitado)
                .orElse(false);
    }

    private PermissaoDTO mapToDTO(Permissao p) {
        return PermissaoDTO.builder()
                .id(p.getId())
                .papel(p.getTipoMembro())
                .funcionalidade(p.getFuncionalidade())
                .habilitado(p.isHabilitado())
                .tituloFuncionalidade(formatarTitulo(p.getFuncionalidade()))
                .build();
    }

    private String formatarTitulo(Funcionalidade f) {
        switch (f) {
            case CRIAR_EVENTOS: 
                return "Criar/Participar de Eventos";
            case VISUALIZAR_FINANCAS: 
                return "Visualizar Tarefas/Despesas";
            case ENVIAR_MENSAGENS: 
                return "Enviar mensagens/comentários";
            case GERENCIAR_MEMBROS: 
                return "Cadastrar/Editar Membros da República";
            case EDITAR_REPUBLICA: 
                return "Editar/Excluir a República";
            case GERENCIAR_FINANCAS: 
                return "Gerenciar Finanças Gerais";
            case GERENCIAR_PERMISSOES: 
                return "Gerenciar Permissões";
            case GERENCIAR_EVENTOS_OUTROS: 
                return "Aprovar/Excluir Eventos Criados por Outros";
            case EDITAR_DADOS_REP: 
                return "Editar Dados Principais da República";
            default: 
                return f.name();
        }
    }
}