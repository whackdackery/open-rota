package com.whackdackery.openrota.app.user.domain.dto;

import lombok.Data;

@Data
public class UserUpdateDto {
    String username;
    String email;
    String password;
}
