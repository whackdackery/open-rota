package com.whackdackery.openrota.app.user.domain;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum AvailableRoles {
    SUPER_ADMIN(1),
    ADMIN(2),
    EDITOR(3),
    SCHEDULER(4),
    END_USER(5);

    private final int value;
    private static final Map<Integer, AvailableRoles> roleMap = new HashMap<>();

    AvailableRoles(int value) {
        this.value = value;
    }

    static {
        for (AvailableRoles availableRoles : AvailableRoles.values()) {
            roleMap.put(availableRoles.value, availableRoles);
        }
    }

    public static Optional<AvailableRoles> valueOf(int statusId) {
        return Optional.ofNullable(roleMap.get(statusId));
    }

    public int getId() {
        return value;
    }


}
