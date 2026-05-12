package servicio_social_api.servicioSocialAPI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("{\"mensaje\": \"Hola ADMIN\"}");
    }
}