package com.realestate.user;

public interface UserService{

    User register(RegisterRequest request);

    LoginResponse login(LoginRequest request);


    String verifyEmail(
            VerifyEmailRequest request);
}
