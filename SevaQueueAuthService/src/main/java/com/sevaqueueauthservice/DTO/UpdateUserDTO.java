package com.sevaqueueauthservice.DTO;

import com.sevaqueueauthservice.entity.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDTO {
    private String name;
    private String mobile;
    private String email;
}

