package csd.app.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import csd.app.payload.request.UpdateUserRequest;
import csd.app.payload.response.MessageResponse;
import csd.app.user.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/api/users/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @PutMapping("api/user/update")
    public ResponseEntity<?> updateUserDetail(@RequestBody UpdateUserRequest updateUser) {

        if (userService.getUserByEmail(updateUser.getEmail()) != null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
        System.out.println(updateUser.toString());

        User user = userService.getUserByUsername(updateUser.getUsername());
        UserInfo userInfo = user.getUserInfo();

        userInfo.setAddress(updateUser.getAddress());
        userInfo.setName(updateUser.getName());
        userInfo.setPhoneNumber(updateUser.getPhoneNumber());
        user.setEmail(updateUser.getEmail());
        user.setUserInfo(userInfo);
        userService.updateUser(user);

        return ResponseEntity.ok(new MessageResponse("Product detail updated successfully"));
        // }
        // return ResponseEntity.badRequest().body((new MessageResponse("Failed to
        // update product detail")));
    }
}