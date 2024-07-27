package com.vinayak.flight_status.api.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vinayak.flight_status.api.users.Users;
import com.vinayak.flight_status.api.users.UsersRepository;
import com.vinayak.flight_status.api.users.UsersResponseDto;
import com.vinayak.flight_status.config.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse signup(Users request) {
        Users user = Users.builder()
                .email(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .fullName(request.getFullName())
                .build();
        usersRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).user(new UsersResponseDto(user)).build();
    }

    public AuthenticationResponse login(Users request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        Users user = usersRepository
                .findByEmail(request.getUsername())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).user(new UsersResponseDto(user)).build();
    }

    public AuthenticationResponse verifyToken(String token) {

        Users user = usersRepository
                .findByEmail(jwtService.extractUsername(token))
                .orElseThrow();
        if (jwtService.isTokenValid(token, user)) {
            return AuthenticationResponse.builder().token(token).user(new UsersResponseDto(user)).build();
        }
        return null;

    }

}
