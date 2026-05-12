package servicio_social_web.servicioSocialWEB;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas del frontend
                        .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()

                        // Rutas por rol
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/profesor/**").hasRole("PROFESOR")
                        .requestMatchers("/alumno/**").hasRole("ALUMNO")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}