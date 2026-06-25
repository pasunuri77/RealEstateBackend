package com.realestate.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String phone;


    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // NEW FIELDS

    @Builder.Default
    private Boolean emailVerified = false;

    private String emailPin;

    private LocalDateTime pinExpiryTime;


    @Column(name = "reset_otp")
    private String resetOtp;

    @Column(name = "reset_otp_expiry")
    private LocalDateTime resetOtpExpiry;

    private String pendingEmail;
}