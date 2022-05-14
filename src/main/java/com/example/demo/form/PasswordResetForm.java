package com.example.demo.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PasswordResetForm {
    private String mailAddress;
    private String token;
    @NotEmpty
    @Size(min = 8, max = 60)
    private String newPassword;
}
