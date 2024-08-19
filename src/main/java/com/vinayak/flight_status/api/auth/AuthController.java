package com.vinayak.flight_status.api.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinayak.flight_status.api.kafka.KafkaProducer;
import com.vinayak.flight_status.api.notification.SaveMessagingTokenData;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    final private AuthService authService;
    final private KafkaProducer kafkaProducer;

    @PostMapping("signup")
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody AuthRequestDto authRequest) {
        AuthenticationResponse signup = authService.signup(authRequest.getUser());

        if (signup == null) {
            return ResponseEntity.internalServerError().build();
        }

        if(authRequest.getFirebase_token() != null){
            kafkaProducer.sendSaveMessagingTokenData(SaveMessagingTokenData.builder().userId(signup.getUser().getId()).firebaseToken(authRequest.getFirebase_token()).build());
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
            kafkaProducer.sendSaveMessagingTokenData(SaveMessagingTokenData.builder().userId(login.getUser().getId()).firebaseToken(authRequest.getFirebase_token()).build());
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
