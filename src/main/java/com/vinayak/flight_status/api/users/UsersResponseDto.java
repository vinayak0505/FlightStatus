package com.vinayak.flight_status.api.users;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsersResponseDto {

    private Integer id;

    private String fullName;

    private String email;

    private UsersRole role;

    // create convertion class form user to userreposponse
    public UsersResponseDto(Users user){
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.role = user.getRole();
    }

}