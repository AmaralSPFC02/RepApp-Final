package com.republica.app.security;

import com.republica.app.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;


public class UserDetailsPrincipal implements UserDetails {

    private Usuario usuario;

    public UserDetailsPrincipal(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return usuario.getId();
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Lugar para devolver os tipos de papeis no futuro (ADM, MEMBRO, etc.)
        return null; 
    }

    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    // Por enquanto, o seguinte se mantem como true
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}