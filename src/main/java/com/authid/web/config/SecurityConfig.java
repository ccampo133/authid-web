package com.authid.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

/**
 * @author Chris Campo
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Configuration
    @Order(1)
    public static class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            // @formatter:off
            http
                .antMatcher("/api/**")
                    .authorizeRequests()
                        .anyRequest().fullyAuthenticated()
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                    .disable();
            // @formatter:on
        }

        @Override
        protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
            // @formatter:off
            auth
                .inMemoryAuthentication()
                    .withUser("apiuser")
                        .password("apipw")
                        .authorities("ROLE_API_USER");
            // @formatter:on
        }
    }

    @Configuration
    @Order(2)
    public static class RootSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        public void configure(final WebSecurity web) throws Exception {
            // @formatter:off
            web
                .ignoring()
                    .antMatchers("/webjars/**",
                            "/images/**",
                            "/css/**",
                            "/oauth/uncache_approvals",
                            "/oauth/cache_approvals");
            // @formatter:on
        }

        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            // @formatter:off
            http
                .authorizeRequests()
                    .antMatchers("/**")
                        .fullyAuthenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .failureUrl("/login?error")
                    .permitAll()
                .and()
                .logout()
                    .permitAll();
            // @formatter:on
        }

        @Autowired
        private DataSource dataSource;

        @Autowired
        private BCryptPasswordEncoder bCryptPasswordEncoder;

        @Override
        public void configure(final AuthenticationManagerBuilder auth) throws Exception {
            // @formatter:off
            auth
                .jdbcAuthentication()
                    .passwordEncoder(bCryptPasswordEncoder)
                    .dataSource(dataSource);
            // @formatter:on
        }
    }
}
