package com.elderdev.orderFlow.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record SupplierRequest(
       @NotBlank String name,
       @NotBlank @Email String email,
       @Length(max = 20) String phone,
       @Length(max = 255) String address
) {}
