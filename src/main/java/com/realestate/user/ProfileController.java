package com.realestate.user;

import com.realestate.exception.BadRequestException;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    // Helper to get logged-in user
    private User getLoggedInUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // 1. GET /api/profile
    @GetMapping
    public ResponseEntity<User> getProfile() {
        User user = getLoggedInUser();
        // Hide password hash for security
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    // 2. PUT /api/profile
    @PutMapping
    public ResponseEntity<User> updateProfile(@RequestBody RegisterRequest request) {
        User user = getLoggedInUser();

        if (request.getPhone() == null || !request.getPhone().matches("[0-9]{10}")) {
            throw new BadRequestException("Invalid 10-digit phone number format");
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());

        User saved = userRepository.save(user);
        saved.setPassword(null);
        return ResponseEntity.ok(saved);
    }

    // 3. POST /api/profile/change-email/request-otp
    @PostMapping("/change-email/request-otp")
    public ResponseEntity<String> requestEmailChangeOtp(@RequestParam String newEmail) {
        User user = getLoggedInUser();

        if (newEmail == null || newEmail.trim().isEmpty() || !newEmail.contains("@")) {
            throw new BadRequestException("Invalid email format");
        }

        if (userRepository.existsByEmail(newEmail)) {
            throw new BadRequestException("Email already in use");
        }

        String pin = String.valueOf(100000 + new SecureRandom().nextInt(900000));
        user.setEmailPin(pin);
        user.setPinExpiryTime(LocalDateTime.now().plusMinutes(10)); // 10 minutes expiry
        user.setPendingEmail(newEmail);
        userRepository.save(user);

        emailService.sendPin(newEmail, pin);

        return ResponseEntity.ok("OTP sent to your new email address");
    }

    // 4. POST /api/profile/change-email/verify-otp
    @PostMapping("/change-email/verify-otp")
    public ResponseEntity<User> verifyEmailChangeOtp(@RequestParam String pin) {
        User user = getLoggedInUser();

        if (user.getEmailPin() == null || !user.getEmailPin().equals(pin)) {
            throw new BadRequestException("Invalid OTP code");
        }

        if (user.getPinExpiryTime() == null || user.getPinExpiryTime().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("OTP code has expired");
        }

        String newEmail = user.getPendingEmail();
        if (newEmail == null || newEmail.trim().isEmpty()) {
            throw new BadRequestException("No pending email change request found");
        }

        if (userRepository.existsByEmail(newEmail)) {
            throw new BadRequestException("Email already in use");
        }

        user.setEmail(newEmail);
        user.setEmailVerified(true);
        user.setEmailPin(null);
        user.setPinExpiryTime(null);
        user.setPendingEmail(null);

        User saved = userRepository.save(user);
        saved.setPassword(null);
        return ResponseEntity.ok(saved);
    }

    // 5. POST /api/profile/change-password/request-otp
    @PostMapping("/change-password/request-otp")
    public ResponseEntity<String> requestPasswordChangeOtp() {
        User user = getLoggedInUser();

        String pin = String.valueOf(100000 + new SecureRandom().nextInt(900000));
        user.setEmailPin(pin);
        user.setPinExpiryTime(LocalDateTime.now().plusMinutes(10)); // 10 minutes expiry
        userRepository.save(user);

        emailService.sendPasswordResetOtp(user.getEmail(), pin);

        return ResponseEntity.ok("OTP sent to your current verified email address");
    }

    // 6. POST /api/profile/change-password/verify-otp
    @PostMapping("/change-password/verify-otp")
    public ResponseEntity<String> verifyPasswordChangeOtp(@RequestParam String pin) {
        User user = getLoggedInUser();

        if (user.getEmailPin() == null || !user.getEmailPin().equals(pin)) {
            throw new BadRequestException("Invalid OTP code");
        }

        if (user.getPinExpiryTime() == null || user.getPinExpiryTime().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("OTP code has expired");
        }

        return ResponseEntity.ok("OTP verified successfully");
    }

    // 7. POST /api/profile/change-password/update
    @PostMapping("/change-password/update")
    public ResponseEntity<String> updatePassword(
            @RequestParam String pin,
            @RequestParam String newPassword,
            @RequestParam String confirmNewPassword) {
        User user = getLoggedInUser();

        if (user.getEmailPin() == null || !user.getEmailPin().equals(pin)) {
            throw new BadRequestException("Invalid OTP code");
        }

        if (user.getPinExpiryTime() == null || user.getPinExpiryTime().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("OTP code has expired");
        }

        if (!newPassword.equals(confirmNewPassword)) {
            throw new BadRequestException("Passwords do not match");
        }

        if (newPassword.length() < 8) {
            throw new BadRequestException("Password must be at least 8 characters long");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setEmailPin(null);
        user.setPinExpiryTime(null);
        userRepository.save(user);

        return ResponseEntity.ok("Password updated successfully");
    }
}
