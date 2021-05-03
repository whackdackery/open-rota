package com.whackdackery.openrota.app.user.config;

import com.whackdackery.openrota.app.user.service.dao.UserDao;
import org.jdbi.v3.core.Jdbi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoRegistration {
    @Bean
    public UserDao userDao(Jdbi jdbi) {
        return jdbi.onDemand(UserDao.class);
    }
}
