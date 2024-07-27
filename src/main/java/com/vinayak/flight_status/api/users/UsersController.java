package com.vinayak.flight_status.api.users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

    final private UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }
}
