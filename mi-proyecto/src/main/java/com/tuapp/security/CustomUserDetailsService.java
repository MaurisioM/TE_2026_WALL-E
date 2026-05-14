package com.tuapp.security;

import com.tuapp.model.Usuario;
import com.tuapp.repository.UsuarioRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        // IMPORTANTE: Devolvemos un UserDetails personalizado que expone la entidad Usuario en getPrincipal()
        return new User(usuario.getEmail(), usuario.getPassword(),
                AuthorityUtils.createAuthorityList("ROLE_" + usuario.getRol())) {
            @Override
            public Object getPrincipal() {
                return usuario;   // Esto permite que el interceptor reciba el objeto Usuario completo
            }
        };
    }
}