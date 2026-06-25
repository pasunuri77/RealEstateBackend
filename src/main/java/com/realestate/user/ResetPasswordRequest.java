package com.realestate.user;


import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordRequest {

    private String email;

    private String otp;

    private String newPassword;
}
