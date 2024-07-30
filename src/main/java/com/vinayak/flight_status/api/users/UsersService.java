package com.vinayak.flight_status.api.users;

import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users getUser(String email) {
        return usersRepository.findByEmail(email.toLowerCase()).orElse(null);
    }

    public Users createUser(Users users) {
        users.setEmail(users.getEmail().toLowerCase()); // setting all emails to lowercase
        return usersRepository.save(users);
    }

    public boolean isAdmin(String email) {
        return getUser(email.toLowerCase()).getRole() == UsersRole.ADMIN;
    }

    public Users loadUserByUsername(String userEmail) {
        return usersRepository.findByEmail(userEmail.toLowerCase()).orElse(null);
    }
}
