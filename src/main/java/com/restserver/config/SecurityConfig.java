package com.restserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;


    private JdbcDaoImpl userDetService() {
        JdbcDaoImpl jdbcDao = new JdbcDaoImpl();
        jdbcDao.setEnableAuthorities(false);
        jdbcDao.setEnableGroups(true);
        jdbcDao.setDataSource(dataSource);
        return jdbcDao;
    }


    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetService())
                .passwordEncoder(passwordEncoder());
    }



    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/**").hasRole("USER")
            .antMatchers(HttpMethod.POST, "/**").hasRole("USER")
            .antMatchers(HttpMethod.PUT, "/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .httpBasic().and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
