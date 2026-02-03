package com.sevaqueueauthservice.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {

    private Long userId;
    private String name;
    private String email;
    private String mobile;

}
