package com.realestate.user;

import java.util.List;

public interface UserService{

    User register(RegisterRequest request);

    LoginResponse login(LoginRequest request);


    String verifyEmail(
            VerifyEmailRequest request);

    String forgotPassword(ForgotPasswordRequest request);

    String resetPassword(ResetPasswordRequest request);


    List<User> getAllUsers();

    User getUserById(Long id);

    User updateUser(Long id, RegisterRequest request);

    void deleteUser(Long id);

    void sendEmailChangeOtp(Long userId, String newEmail);

    User confirmEmailChange(Long userId, String newEmail, String pin);
}
