package com.tareq.springboot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String[] Public_EndPoints = {
            "/api/v1/auth/**"
    };
    @Bean // should exist to use an Autowired Authentication in AuthController class
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    @Bean
    AuthFilter authFilter(){
        return new AuthFilter();
    }
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
                .addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class); // add filter before any request (after signed in)
    }
}
