package com.authid.web.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

/**
 * @author Chris Campo
 */
@Configuration
public class ApplicationConfig {

    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService(@NotNull final DataSource dataSource) {
        return new JdbcClientDetailsService(dataSource);
    }
}
