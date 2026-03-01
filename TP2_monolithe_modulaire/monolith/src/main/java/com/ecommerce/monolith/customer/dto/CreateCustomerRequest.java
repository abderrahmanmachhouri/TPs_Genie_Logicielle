package com.ecommerce.monolith.customer.dto;


import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class CreateCustomerRequest {
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;
    private String phone;
    private String address;
}