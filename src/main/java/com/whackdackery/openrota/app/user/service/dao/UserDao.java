package com.whackdackery.openrota.app.user.service.dao;

import com.whackdackery.openrota.app.user.domain.Role;
import com.whackdackery.openrota.app.user.domain.User;
import com.whackdackery.openrota.app.user.domain.UserWithRoles;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;

import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(value = User.class, prefix = "u")
@RegisterBeanMapper(value = UserWithRoles.class, prefix = "u")
@RegisterBeanMapper(value = Role.class, prefix = "r")
@UseClasspathSqlLocator
public interface UserDao {

    @SqlQuery
    List<User> getAllUsers();

    @SqlQuery
    @UseRowReducer(UserRoleMapper.class)
    Optional<UserWithRoles> getUserWithRoles(int userId);

}
