package com.whackdackery.openrota.app.user.service.dao;

import com.whackdackery.openrota.app.user.domain.Role;
import com.whackdackery.openrota.app.user.domain.User;
import com.whackdackery.openrota.app.user.domain.dto.UserUpdateDto;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;

import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(value = UserUpdateDto.class, prefix = "u")
@RegisterBeanMapper(value = User.class, prefix = "u")
@RegisterBeanMapper(value = Role.class, prefix = "r")
@UseClasspathSqlLocator
public interface UserDao {

    @SqlQuery
    @UseRowReducer(UserRoleMapper.class)
    List<User> getAllUsers();

    @SqlQuery
    @UseRowReducer(UserRoleMapper.class)
    Optional<User> getUserById(int userId);

    @SqlQuery
    @UseRowReducer(UserRoleMapper.class)
    Optional<User> getUserByUsername(String username);

    @SqlQuery
    @UseRowReducer(UserRoleMapper.class)
    Optional<User> getUserByEmail(String email);

    @SqlUpdate
    @GetGeneratedKeys("id")
    int createUser(@BindBean("user") UserUpdateDto user);
}
