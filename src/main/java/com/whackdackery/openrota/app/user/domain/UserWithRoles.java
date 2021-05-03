package com.whackdackery.openrota.app.user.domain;

import lombok.Data;

import java.util.List;

@Data
public class UserWithRoles {
    Long id;
    String username;
    String email;
    List<Role> roles;
}
