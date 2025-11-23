package com.republica.app.service;

import com.republica.app.dto.AuthenticationResponseDTO;
import com.republica.app.dto.LoginRequestDTO;
import com.republica.app.model.Usuario;
import com.republica.app.model.UsuarioRep;
import com.republica.app.repository.UsuarioRepository;
import com.republica.app.repository.UsuarioRepRepository; 
import com.republica.app.security.JwtService;
import com.republica.app.security.UserDetailsPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioRepRepository usuarioRepRepository; 

    public AuthenticationResponseDTO login(LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
        );
        
        var userDetails = (UserDetailsPrincipal) authentication.getPrincipal();
        var jwtToken = jwtService.generateToken(userDetails);
        
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        List<UsuarioRep> vinculaciones = usuarioRepRepository.findByUsuarioId(usuario.getId());
        
        Long repId = null;
        if (!vinculaciones.isEmpty()) {
            vinculaciones.sort(Comparator.comparingInt(v -> v.getTipoMembro().ordinal()));
            
            repId = vinculaciones.get(0).getRep().getId();
        }

        return AuthenticationResponseDTO.builder()
                .id(usuario.getId())
                .nome(usuario.getNomeCompleto())
                .token(jwtToken)
                .repId(repId)
                .build();
    }

}