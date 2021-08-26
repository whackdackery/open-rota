package com.whackdackery.openrota.app.user.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserWithRoles {
    int id;
    @NotBlank
    String username;
    @NotBlank
    String email;
    @NotNull
    List<Role> roles;
}
