package com.whackdackery.openrota.app.user.domain;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class User {
    int id;
    @Email
    String username;
    @NotBlank
    String email;
}
