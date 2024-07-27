package com.vinayak.flight_status.users;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

    private UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("signup")
    public ResponseEntity<Void> signup(@RequestBody Users users) {

        Users newuser = usersService.createUser(users);

        if(newuser == null){
            return ResponseEntity.internalServerError().build();
        }

        URI locationOfNewUser = URI.create("/users/" + newuser.getId());
        return ResponseEntity
                .created(locationOfNewUser)
                .build();
    }

    @PostMapping("login")
    public ResponseEntity<UsersResponseDto> login(@RequestBody Users user) {
        
        Users dbuser = usersService.getUser(user.getEmail());

        if(dbuser == null || !dbuser.getPassword().contentEquals(user.getPassword())){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(new UsersResponseDto(dbuser));
    }
}
