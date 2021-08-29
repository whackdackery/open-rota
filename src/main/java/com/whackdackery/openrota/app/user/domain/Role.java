package com.whackdackery.openrota.app.user.domain;

import com.whackdackery.openrota.app.user.domain.AvailableRoles;
import lombok.Data;

@Data
public class Role {
    int id;
    String code;

    public Role(AvailableRoles role) {
        this.id = role.getId();
        this.code = role.name();
    }

    public Role() {
    }
}
