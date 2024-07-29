package com.vinayak.flight_status.api.auth;

import com.vinayak.flight_status.api.users.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDto {

    private String firebase_token;
    private Users user;
}
