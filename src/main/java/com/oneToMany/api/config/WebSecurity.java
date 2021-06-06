package com.oneToMany.api.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("{noop}user123")
                .roles("USER")
                .and()
                .withUser("admin")
                .password("{noop}admin123")
                .roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**")
                .hasAnyRole("USER")
                .antMatchers(HttpMethod.POST, "api/**")
                .hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "api/**")
                .hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "api/**")
                .hasAnyRole("ADMIN")
                .and()
                .csrf()
                .disable()
                .formLogin()
                .disable();
    }
}
