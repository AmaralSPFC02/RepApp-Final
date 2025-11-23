package com.republica.app.service;

import com.republica.app.dto.CadastroRequestDTO;
import com.republica.app.dto.UsuarioUpdateDTO;
import com.republica.app.model.Usuario;
import com.republica.app.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor; 
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pela lógica de negócio dos usuários.
 * Ele orquestra as operações de CRUD usando o UsuarioRepository.
 */
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Salva um novo usuário no banco de dados.
     * @param usuario O objeto Usuario a ser salvo.
     * @return O usuário salvo, incluindo seu ID gerado.
     */
    public Usuario salvarUsuario(CadastroRequestDTO cadastroDTO) {
        if (usuarioRepository.existsByEmail(cadastroDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este email já está em uso.");
        }

        Usuario novUsuario = new Usuario();
        novUsuario.setNomeCompleto(cadastroDTO.getNomeCompleto());
        novUsuario.setEmail(cadastroDTO.getEmail());
        novUsuario.setSenha(passwordEncoder.encode(cadastroDTO.getSenha()));
        novUsuario.setFotoUrl(cadastroDTO.getFotoUrl());
        novUsuario.setCurso(cadastroDTO.getCurso());
        novUsuario.setUniversidade(cadastroDTO.getUniversidade());
        novUsuario.setAno(cadastroDTO.getAno());

        return usuarioRepository.save(novUsuario);
        // NOTA: Idealmente, este método deveria devolver um DTO sem a senha.
    }

    /**
     * Busca um usuário pelo seu ID.
     * @param id O ID do usuário.
     * @return O usuário encontrado.
     * @throws ResponseStatusException se o usuário não for encontrado.
     */
    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com o ID: " + id));
    }

    /**
     * Busca todos os usuários.
     * @return Uma lista de todos os usuários.
     */
    public List<Usuario> buscarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Atualiza os dados de um usuário existente.
     * @param id O ID do usuário a ser atualizado.
     * @param usuarioDetalhes O objeto Usuario com os dados a serem atualizados.
     * @return O usuário atualizado.
     * @throws ResponseStatusException se o usuário não for encontrado.
     */
    @Transactional
    public Usuario atualizarUsuario(Long id, UsuarioUpdateDTO updateDTO) {
        Usuario usuario = buscarUsuarioPorId(id);

        Optional.ofNullable(updateDTO.getNomeCompleto()).ifPresent(usuario::setNomeCompleto);
        Optional.ofNullable(updateDTO.getFotoUrl()).ifPresent(usuario::setFotoUrl);
        Optional.ofNullable(updateDTO.getCurso()).ifPresent(usuario::setCurso);
        Optional.ofNullable(updateDTO.getUniversidade()).ifPresent(usuario::setUniversidade);
        Optional.ofNullable(updateDTO.getAno()).ifPresent(usuario::setAno);
        
        return usuarioRepository.save(usuario);
    }

    /**
     * Deleta um usuário pelo seu ID.
     * @param id O ID do usuário a ser deletado.
     * @throws ResponseStatusException se o usuário não for encontrado.
     */
    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com o ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
