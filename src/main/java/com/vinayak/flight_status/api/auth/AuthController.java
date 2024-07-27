package com.vinayak.flight_status.api.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinayak.flight_status.api.users.Users;

@RestController
@RequestMapping("/auth")
public class AuthController {

    final private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("signup")
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody Users users) {

        AuthenticationResponse signup = authService.signup(users);

        if (signup == null) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(signup);
    }

    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody Users users) {
        AuthenticationResponse login = authService.login(users);
        if (login == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(login);
    }

    @PostMapping("verifyToken")
    public ResponseEntity<AuthenticationResponse> verifyToken(String token) {

        AuthenticationResponse verifyToken = authService.verifyToken(token);

        if (verifyToken == null) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().body(verifyToken);
    }

}
