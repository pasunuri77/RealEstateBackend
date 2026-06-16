package com.realestate.purchase;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseRequest {

    @NotNull(message = "Unit Id is required")

    private Long unitId;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @Email(message = "Invalid email")
    private String email;

    @NotNull(message = "Budget is required")
    private Double budget;



    private String message;

    private LocalDateTime createdAt;
}
