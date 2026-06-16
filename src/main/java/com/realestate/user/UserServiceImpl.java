package com.realestate.user;

import com.realestate.exception.BadRequestException;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.repositories.UserRepo;
import com.realestate.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

}
