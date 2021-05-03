package com.whackdackery.openrota.config;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JdbiConfiguration {
    @Bean
    public Jdbi jdbi(DataSource datasource){
        return Jdbi.create(datasource)
                .installPlugins()
                .installPlugin(new SqlObjectPlugin());
    }
}
