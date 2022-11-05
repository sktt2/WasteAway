package csd.app.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import csd.app.payload.request.UpdateUserRequest;
import csd.app.payload.response.MessageResponse;
import csd.app.payload.response.UserInfoResponse;
import csd.app.user.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/api/users/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(new UserInfoResponse(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getUserInfo()));

    }

    @PutMapping("api/users/update")
    public ResponseEntity<?> updateUserDetail(@RequestBody UpdateUserRequest updateUser) {
        User checkUser = userService.getUserByEmail(updateUser.getEmail());
        User user = userService.getUserByUsername(updateUser.getUsername());
        if (checkUser != null && checkUser != user) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        UserInfo userInfo = user.getUserInfo();

        // Validation check for updating user details 
        try {
            userInfo.setAddress(updateUser.getAddress());
            userInfo.setName(updateUser.getName());
            userInfo.setPhoneNumber(updateUser.getPhoneNumber());
            user.setEmail(updateUser.getEmail());
            user.setUserInfo(userInfo);
            userService.updateUser(user);
            return ResponseEntity.ok(new MessageResponse("User updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid Details"));
        }
    }

    
}