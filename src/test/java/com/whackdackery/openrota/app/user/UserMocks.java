package com.whackdackery.openrota.app.user;

import com.whackdackery.openrota.app.user.domain.Role;
import com.whackdackery.openrota.app.user.domain.User;
import com.whackdackery.openrota.app.user.domain.UserWithRoles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserMocks {
    public static List<User> singleUserInList() {
        List<User> userList = new ArrayList<>();
        User user = createBasicUser(1, "userone", "userone@email.com");
        userList.add(user);

        return userList;
    }

    public static List<User> threeUsersInList() {
        List<User> userList = singleUserInList();
        User userTwo = createBasicUser(2, "usertwo", "usertwo@email.com");
        userList.add(userTwo);
        User userThree = createBasicUser(3, "userthree", "userthree@email.com");
        userList.add(userThree);

        return userList;
    }

    public static UserWithRoles userWithRoles() {
        UserWithRoles user = new UserWithRoles();
        user.setId(1);
        user.setUsername("userone");
        user.setEmail("userone@email.com");
        user.setRoles(Collections.singletonList(Role.SUPER_ADMIN));

        return user;
    }

    private static User createBasicUser(int i, String userone, String s) {
        User user = new User();
        user.setId(i);
        user.setUsername(userone);
        user.setEmail(s);

        return user;
    }
}
