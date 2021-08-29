package com.whackdackery.openrota.app.user.service.dao;

import com.whackdackery.openrota.app.user.domain.Role;
import com.whackdackery.openrota.app.user.domain.User;
import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;

import java.util.ArrayList;
import java.util.Map;

public class UserRoleMapper implements LinkedHashMapRowReducer<Integer, User> {

    public static final String USER_ID_COLUMN = "u_id";
    public static final String ROLE_ID_COLUMN = "r_id";

    @Override
    public void accumulate(Map<Integer, User> map, RowView rowView) {
        User u = map.computeIfAbsent(rowView.getColumn(USER_ID_COLUMN, Integer.class),
                id -> rowView.getRow(User.class));
        if (rowView.getColumn(ROLE_ID_COLUMN, Integer.class) != null) {
            if (u.getRoles() == null) {
                u.setRoles(new ArrayList<>());
            }
            u.getRoles().add(rowView.getRow(Role.class));
        }
    }
}
