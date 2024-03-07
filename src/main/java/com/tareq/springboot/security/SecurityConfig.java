package com.tareq.springboot.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String[] Public_EndPoints = {
            "/api/v1/auth/**"
    };

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .cors().and().csrf().disable() // No Cookies
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // we're using JWT Auth -> we don't want session (STATELESS)
                .and()
                .authorizeRequests() // this method decide what URLs u want it to be public and what's not
                    .antMatchers(Public_EndPoints).permitAll() // permission for all users to access this URL
                    .anyRequest().authenticated() // for any other request ... there is no public permission (just for authenticated users)
                .and()
                .httpBasic(); // this method make the authentication based on just username (we want it temporary)
    }
}
