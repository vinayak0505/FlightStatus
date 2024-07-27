package com.vinayak.flight_status.api.auth;

import com.vinayak.flight_status.api.users.UsersResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    private String token;

    private UsersResponseDto user;
}
