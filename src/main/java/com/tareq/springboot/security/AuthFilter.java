package com.tareq.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends OncePerRequestFilter {

    @Value(value = "${auth.header}")
    private String TOKEN_HEADER; // with the Authorization in postman (Bearer)

    @Bean
    TokenUtil tokenUtil(){
        return new TokenUtil();
    }

    @Bean
    UserService userService(){
        return new UserService();
    }

    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // we want to fetch the token then make sure if its valid
        final String header = request.getHeader(TOKEN_HEADER);
        final SecurityContext securityContext = SecurityContextHolder.getContext();

        if (header != null && securityContext.getAuthentication() == null) { // check if the token is valid
            String token = header.substring("Bearer ".length()); // remove "Bearer " from token
            String username = tokenUtil.getUserNameFromToken(token); // get username from token
            if(username != null){
                UserDetails userDetails = userService.loadUserByUsername(username); // get username from database
                if(tokenUtil.isTokenValid(token, userDetails)){ // if token is valid set the user as authenticated
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request,response); // let the request pass
    }
}
