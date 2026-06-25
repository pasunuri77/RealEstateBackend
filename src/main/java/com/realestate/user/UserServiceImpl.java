package com.realestate.user;

import com.realestate.exception.BadRequestException;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.repositories.UserRepo;
import com.realestate.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    @Override
    public User register(
            RegisterRequest request) {

        if (userRepository.existsByEmail(
                request.getEmail())) {

            throw new BadRequestException(
                    "Email already exists");
        }

        String pin = String.valueOf(
                100000 +
                        new java.security.SecureRandom()
                                .nextInt(900000));

        User user = User.builder()
                .firstName(
                        request.getFirstName())
                .lastName(
                        request.getLastName())
                .email(
                        request.getEmail())
                .phone(
                        request.getPhone())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()))
                .role(
                        Role.valueOf(
                                request.getRole()))
                .emailVerified(false)
                .emailPin(pin)
                .pinExpiryTime(
                        LocalDateTime.now()
                                .plusMinutes(5))
                .build();

        User savedUser =
                userRepository.save(user);

        emailService.sendPin(
                savedUser.getEmail(),
                pin);

        return savedUser;
    }

    @Override
    public LoginResponse login(
            LoginRequest request) {

        User user = userRepository.findByEmail(
                        request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        if (!user.getEmailVerified()) {

            throw new BadRequestException(
                    "Please verify your email first");
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new BadRequestException(
                    "Invalid email or password");
        }

        String token =
                jwtUtil.generateToken(
                        user.getEmail(),
                        user.getRole().name());

        return LoginResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole().name())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .id(user.getId())
                .build();
    }

    @Override
    public String verifyEmail(
            VerifyEmailRequest request) {

        User user =
                userRepository.findByEmail(
                                request.getEmail())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"));

        if (user.getPinExpiryTime()
                .isBefore(
                        LocalDateTime.now())) {

            throw new BadRequestException(
                    "PIN Expired");
        }

        if (!user.getEmailPin()
                .equals(
                        request.getPin())) {

            throw new BadRequestException(
                    "Invalid PIN");
        }

        user.setEmailVerified(true);
        user.setEmailPin(null);
        user.setPinExpiryTime(null);

        userRepository.save(user);

        return "Email Verified Successfully";
    }

    @Override
    public String forgotPassword(
            ForgotPasswordRequest request) {

        User user = userRepository.findByEmail(
                        request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        String otp = String.valueOf(
                100000 +
                        new SecureRandom()
                                .nextInt(900000));

        user.setResetOtp(otp);

        user.setResetOtpExpiry(
                LocalDateTime.now()
                        .plusMinutes(5));

        userRepository.save(user);

        emailService.sendPasswordResetOtp(
                user.getEmail(),
                otp);

        return "Password reset OTP sent successfully";
    }

    @Override
    public String resetPassword(
            ResetPasswordRequest request) {

        User user =
                userRepository.findByEmail(
                                request.getEmail())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"));

        if (user.getResetOtp() == null) {

            throw new BadRequestException(
                    "Reset OTP not generated");
        }

        if (!user.getResetOtp()
                .equals(request.getOtp())) {

            throw new BadRequestException(
                    "Invalid OTP");
        }

        if (user.getResetOtpExpiry()
                .isBefore(
                        LocalDateTime.now())) {

            throw new BadRequestException(
                    "OTP Expired");
        }

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()));

        user.setResetOtp(null);
        user.setResetOtpExpiry(null);

        userRepository.save(user);

        return "Password reset successful";
    }

    @Override
    public java.util.List<User> getAllUsers() {
        java.util.List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found");
        }
        return users;
    }

    @Override
    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));
    }

    @Override
    public User updateUser(
            Long id,
            RegisterRequest request) {

        User user =
                userRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"));

        user.setFirstName(
                request.getFirstName());

        user.setLastName(
                request.getLastName());

        user.setEmail(
                request.getEmail());

        user.setPhone(
                request.getPhone());

        user.setRole(
                Role.valueOf(
                        request.getRole()));

        if(request.getPassword() != null
                && !request.getPassword().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(
                            request.getPassword()));
        }

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {

        User user =
                userRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"));

        userRepository.delete(user);
    }

    @Override
    public void sendEmailChangeOtp(Long userId, String newEmail) {
        if (userRepository.existsByEmail(newEmail)) {
            throw new BadRequestException("Email already in use");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String pin = String.valueOf(100000 + new java.security.SecureRandom().nextInt(900000));
        user.setEmailPin(pin);
        user.setPinExpiryTime(LocalDateTime.now().plusMinutes(5));
        userRepository.save(user);

        emailService.sendPin(newEmail, pin);
    }

    @Override
    public User confirmEmailChange(Long userId, String newEmail, String pin) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getPinExpiryTime() == null || user.getPinExpiryTime().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("OTP Expired");
        }

        if (user.getEmailPin() == null || !user.getEmailPin().equals(pin)) {
            throw new BadRequestException("Invalid OTP");
        }

        if (userRepository.existsByEmail(newEmail)) {
            throw new BadRequestException("Email already in use");
        }

        user.setEmail(newEmail);
        user.setEmailVerified(true);
        user.setEmailPin(null);
        user.setPinExpiryTime(null);
        return userRepository.save(user);
    }

}
