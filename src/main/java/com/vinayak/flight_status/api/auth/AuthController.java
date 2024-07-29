package com.vinayak.flight_status.api.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinayak.flight_status.api.notification.NotificationService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    final private AuthService authService;
    final private NotificationService notificationService;

    public AuthController(AuthService authService, NotificationService notificationService) {
        this.authService = authService;
        this.notificationService = notificationService;
    }

    @PostMapping("signup")
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody AuthRequestDto authRequest) {

        AuthenticationResponse signup = authService.signup(authRequest.getUser());

        if (signup == null) {
            return ResponseEntity.internalServerError().build();
        }

        if(authRequest.getFirebase_token() != null){
            notificationService.saveMessagingToken(signup.getUser().getId(),authRequest.getFirebase_token());
        }

        return ResponseEntity.ok(signup);
    }

    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthRequestDto authRequest) {
        AuthenticationResponse login = authService.login(authRequest.getUser());
        if (login == null) {
            return ResponseEntity.internalServerError().build();
        }
        if(authRequest.getFirebase_token() != null){
            notificationService.saveMessagingToken(login.getUser().getId(),authRequest.getFirebase_token());
        }else{

        }
        return ResponseEntity.ok(login);
    }

    @PostMapping("verifyToken")
    public ResponseEntity<AuthenticationResponse> verifyToken(@RequestBody String token) {

        AuthenticationResponse verifyToken = authService.verifyToken(token);

        if (verifyToken == null) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().body(verifyToken);
    }

}
