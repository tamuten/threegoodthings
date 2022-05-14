package com.example.demo.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class PasswordReissueForm {
    @NotEmpty
    @Email
    private String mailAddress;
}
