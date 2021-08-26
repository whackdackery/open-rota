package com.whackdackery.openrota.app.user.domain;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum Role {
    SUPER_ADMIN(1),
    ADMIN(2),
    EDITOR(3),
    SCHEDULER(4),
    END_USER(5);

    private final int value;
    private static final Map<Integer, Role> roleMap = new HashMap<>();

    Role(int value) {
        this.value = value;
    }

    static {
        for (Role role : Role.values()) {
            roleMap.put(role.value, role);
        }
    }

    public static Optional<Role> valueOf(int statusId) {
        return Optional.ofNullable(roleMap.get(statusId));
    }

    public int getValue() {
        return value;
    }

}
