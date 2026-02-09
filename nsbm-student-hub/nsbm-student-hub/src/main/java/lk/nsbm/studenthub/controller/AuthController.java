package lk.nsbm.studenthub.controller;

import jakarta.validation.Valid;
import lk.nsbm.studenthub.dto.RegisterRequest;
import lk.nsbm.studenthub.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Register a USER (password encrypted, role stored in DB)
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@Valid @RequestBody RegisterRequest req) {
        return authService.register(req);
    }

    // Helpful endpoint to verify login (Basic Auth)
    @GetMapping("/me")
    public Object me(Authentication authentication) {
        return authentication;
    }
}
