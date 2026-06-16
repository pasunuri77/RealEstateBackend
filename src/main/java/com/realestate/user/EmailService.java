package com.realestate.user;

public interface EmailService {
    void sendPin(
            String email,
            String pin);
}
