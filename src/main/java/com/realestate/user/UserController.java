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

    @PostMapping("/forgot-password")
    public ResponseEntity<String>
    forgotPassword(
            @RequestBody
            ForgotPasswordRequest request) {

        return ResponseEntity.ok(
                userService.forgotPassword(
                        request));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String>
    resetPassword(
            @RequestBody
            ResetPasswordRequest request) {

        return ResponseEntity.ok(
                userService.resetPassword(
                        request));
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(
                userService.updateUser(
                        id,
                        request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.ok(
                "User deleted successfully");
    }

    @PostMapping("/send-email-change-otp")
    public ResponseEntity<String> sendEmailChangeOtp(
            @RequestParam Long userId,
            @RequestParam String newEmail) {
        
        userService.sendEmailChangeOtp(userId, newEmail);
        return ResponseEntity.ok("OTP sent successfully to your new email address");
    }

    @PostMapping("/confirm-email-change")
    public ResponseEntity<User> confirmEmailChange(
            @RequestParam Long userId,
            @RequestParam String newEmail,
            @RequestParam String pin) {
        
        return ResponseEntity.ok(userService.confirmEmailChange(userId, newEmail, pin));
    }
}
