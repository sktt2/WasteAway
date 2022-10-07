package csd.app.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import csd.app.user.User;
import csd.app.user.UserRepository;

@RestController
public class UserController {
    private UserRepository users;

    public UserController(UserRepository users) {
        this.users = users;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return users.findAll();
    }

    @GetMapping("/api/auth/users/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return users.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Error: User not found."));
    }
}