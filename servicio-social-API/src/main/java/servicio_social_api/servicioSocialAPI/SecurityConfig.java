package servicio_social_api.servicioSocialAPI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpStatus;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //Deshabilitar CSRF
                .csrf(csrf -> csrf.disable())

                //Reglas de acceso por URL
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas
                        .requestMatchers("/api/auth/**").permitAll()

                        // Rutas por rol
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/profesor/**").hasRole("PROFESOR")
                        .requestMatchers("/api/alumno/**").hasRole("ALUMNO")

                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated()
                )

                // Manejo de errores
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            // 401 - No autenticado
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\": \"No autenticado. Por favor inicia sesión.\"}");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            // 403 - Sin permisos
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\": \"Acceso denegado. No tienes permisos para este recurso.\"}");
                        })
                )

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}