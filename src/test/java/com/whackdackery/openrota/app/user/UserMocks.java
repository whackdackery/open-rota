package com.whackdackery.openrota.app.user;

import com.whackdackery.openrota.app.user.domain.AvailableRoles;
import com.whackdackery.openrota.app.user.domain.Role;
import com.whackdackery.openrota.app.user.domain.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserMocks {

    public static User singleUser() {
        return singleUserInList().get(0);
    }

    public static List<User> singleUserInList() {
        List<User> userList = new ArrayList<>();
        User superAdminUser = createSingleRoleUser(1, "superadmin", "superadmin@email.com", AvailableRoles.SUPER_ADMIN);
        userList.add(superAdminUser);

        return userList;
    }

    public static List<User> threeUsersInList() {
        List<User> userList = singleUserInList();
        User adminUser = createSingleRoleUser(2, "admin", "admin@email.com", AvailableRoles.ADMIN);
        userList.add(adminUser);

        User editorUser = createSingleRoleUser(3, "editor", "editor@email.com", AvailableRoles.EDITOR);
        userList.add(editorUser);

        return userList;
    }

    private static User createSingleRoleUser(int userId, String username, String email, AvailableRoles role) {
        return createMultiRoleUser(userId, username, email, Collections.singletonList(role));
    }

    private static User createMultiRoleUser(int userId, String username, String email, List<AvailableRoles> roles) {
        User user = new User();
        user.setId(userId);
        user.setUsername(username);
        user.setEmail(email);

        user.setRoles(new ArrayList<>());
        roles.forEach(availableRole -> user.getRoles().add(new Role(availableRole)));

        return user;
    }


}
