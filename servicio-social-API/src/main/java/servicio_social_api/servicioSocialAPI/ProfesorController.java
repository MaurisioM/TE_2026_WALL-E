package servicio_social_api.servicioSocialAPI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profesor")
@PreAuthorize("hasRole('PROFESOR')")
public class ProfesorController {

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("{\"mensaje\": \"Hola PROFESOR\"}");
    }
}
