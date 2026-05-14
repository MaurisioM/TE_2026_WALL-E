package com.tuapp.interceptor;

import com.tuapp.model.Usuario; // Ajusta el import según tu entidad Usuario
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ProfesorResourceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 1. Usuario no autenticado
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Usuario no autenticado.\"}");
            return false;
        }

        // 2. Obtener el usuario autenticado
        Object principal = auth.getPrincipal();
        if (!(principal instanceof Usuario)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\": \"Error de autenticación.\"}");
            return false;
        }
        Usuario usuarioActual = (Usuario) principal;

        // 3. ADMIN tiene acceso total
        if ("ADMIN".equals(usuarioActual.getRol())) {
            return true;
        }

        // 4. Solo PROFESOR puede continuar
        if (!"PROFESOR".equals(usuarioActual.getRol())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Acceso denegado. Solo profesores pueden acceder a estos recursos.\"}");
            return false;
        }

        // 5. Extraer ID de la URL (ej: /api/profesor/5/programas)
        Long idProfesorSolicitado = extraerIdProfesor(request.getRequestURI());
        if (idProfesorSolicitado == null) {
            // No es ruta que requiera validación (ej: /profesor/dashboard) -> permitir
            return true;
        }

        // 6. Validar que el ID coincida con el del profesor autenticado
        if (!usuarioActual.getId().equals(idProfesorSolicitado)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"No tienes acceso a los recursos de otro profesor.\"}");
            return false;
        }

        return true;
    }

    private Long extraerIdProfesor(String path) {
        Pattern pattern = Pattern.compile("/api/profesor/(\\d+)");
        Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }
        return null;
    }
}