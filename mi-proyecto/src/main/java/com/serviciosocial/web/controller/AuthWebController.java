package com.serviciosocial.web.controller;

import com.serviciosocial.web.dto.AuthRequest;
import com.serviciosocial.web.dto.AuthResponse;
import com.serviciosocial.web.dto.UsuarioInfoDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Controller
public class AuthWebController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.base.url:http://localhost:8080}")
    private String apiBaseUrl;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session) {
        AuthRequest authRequest = new AuthRequest(username, password);
        String loginUrl = apiBaseUrl + "/api/auth/login";

        try {
            ResponseEntity<AuthResponse> response = restTemplate.postForEntity(loginUrl, authRequest, AuthResponse.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                AuthResponse authResponse = response.getBody();
                session.setAttribute("token", authResponse.getToken());
                session.setAttribute("rol", authResponse.getRol());

                UsuarioInfoDTO usuario = new UsuarioInfoDTO();
                usuario.setUsername(username);
                usuario.setRol(authResponse.getRol());
                session.setAttribute("usuario", usuario);

                String rol = authResponse.getRol();
                switch (rol.toUpperCase()) {
                    case "ADMIN":
                        return "redirect:/admin/dashboard";
                    case "PROFESOR":
                        return "redirect:/profesor/dashboard";
                    case "ALUMNO":
                        return "redirect:/alumno/dashboard";
                    default:
                        return "redirect:/login?error=true";
                }
            } else {
                return "redirect:/login?error=true";
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 401) {
                return "redirect:/login?error=true";
            }
            return "redirect:/login?error=true";
        } catch (Exception e) {
            return "redirect:/login?error=true";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token != null) {
            String logoutUrl = apiBaseUrl + "/api/auth/logout";
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            try {
                restTemplate.exchange(logoutUrl, HttpMethod.POST, entity, Void.class);
            } catch (Exception e) {
                // ignore
            }
        }
        session.invalidate();
        return "redirect:/login";
    }
}