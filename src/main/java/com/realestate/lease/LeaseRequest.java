package com.realestate.lease;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeaseRequest {
    @NotNull(message = "Unit Id is required")
    private Long unitId;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @Email(message = "Invalid email")
    private String email;

    private String businessType;

    private String leaseType;

    private RequestStatus status;

    private LocalDate preferredStartDate;

    private String message;

    private LocalDateTime createdAt;
}
