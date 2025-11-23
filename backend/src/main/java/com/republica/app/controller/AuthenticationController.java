package com.republica.app.controller;

import com.republica.app.dto.AuthenticationResponseDTO;
import com.republica.app.dto.CadastroRequestDTO;
import com.republica.app.dto.LoginRequestDTO;
import com.republica.app.model.Usuario;
import com.republica.app.security.UserDetailsPrincipal;
import com.republica.app.security.JwtService;
import com.republica.app.service.AuthenticationService;
import com.republica.app.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints para Login e Registro de Usuarios")
public class AuthenticationController {

    private final UsuarioService usuarioService;
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @Operation(summary = "Registrar Usuario", description = "Cria un novo usuario e devolve o token automáticamente")
    @PostMapping("/cadastro")
    public ResponseEntity<AuthenticationResponseDTO> criarUsuario(@RequestBody CadastroRequestDTO cadastroDTO) {
        Usuario novoUsuario = usuarioService.salvarUsuario(cadastroDTO);
        
        UserDetailsPrincipal userDetails = new UserDetailsPrincipal(novoUsuario);
        String jwtToken = jwtService.generateToken(userDetails);

        AuthenticationResponseDTO response = AuthenticationResponseDTO.builder()
                .id(novoUsuario.getId())
                .nome(novoUsuario.getNomeCompleto())
                .token(jwtToken)
                .repId(null)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @Operation(summary = "Login", description = "Autentica o usuario e devolve Token + repId para redirecionar")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }
}