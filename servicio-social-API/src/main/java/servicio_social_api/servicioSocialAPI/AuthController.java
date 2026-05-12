package servicio_social_api.servicioSocialAPI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody(required = false) Object request) {
        return ResponseEntity.ok("{\"mensaje\": \"Login OK\"}");
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginGet() {
        return ResponseEntity.ok("{\"mensaje\": \"Endpoint de auth activo\"}");
    }
}