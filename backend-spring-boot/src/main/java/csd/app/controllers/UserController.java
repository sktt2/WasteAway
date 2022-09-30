package csd.app.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import csd.app.user.Status;
import csd.app.user.User;
import csd.app.user.UserRepository;

@RestController
public class UserController {
    private UserRepository users;
    private BCryptPasswordEncoder encoder;

    public UserController(UserRepository users, BCryptPasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return users.findAll();
    }

    @GetMapping("/api/auth/users/{username}") 
    public User getUserByUsername(@PathVariable String username) {
        return users.findByUsername(username).get();
    }

    /**
     * Using BCrypt encoder to encrypt the password for storage
     * 
     * @param user
     * @return
     */
    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
        // your code here
        return users.save(user);
    }

}