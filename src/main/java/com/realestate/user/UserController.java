package com.realestate.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                userService.login(request));
    }

    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/verify-email")
    public ResponseEntity<String>
    verifyEmail(
            @RequestBody
            VerifyEmailRequest request) {

        return ResponseEntity.ok(
                userService.verifyEmail(
                        request));
    }

}
