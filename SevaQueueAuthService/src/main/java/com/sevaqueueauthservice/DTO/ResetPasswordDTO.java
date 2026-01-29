package com.sevaqueueauthservice.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class ResetPasswordDTO {
    private String email;
    private String newPassword;
}
