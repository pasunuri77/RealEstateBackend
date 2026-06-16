package com.realestate.user;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifyEmailRequest {
    private String email;

    private String pin;

}
