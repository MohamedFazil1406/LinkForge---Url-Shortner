package com.linkForge.app.controller;

import com.linkForge.app.model.User;
import com.linkForge.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        User registerUser = userService.registerUser(user);
        if (registerUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Register SuccessFull");
        }
        return ResponseEntity.badRequest()
                .body("User already exists");


    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {

        String token = userService.loginUser(user);

        if(token == null) {
            return ResponseEntity.badRequest()
                    .body("Invalid username or password");
        }

        return ResponseEntity.ok(token);
    }
}
