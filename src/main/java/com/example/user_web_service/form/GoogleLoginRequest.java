package com.example.user_web_service.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class GoogleLoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String idToken;

    // Getters và setters
}
