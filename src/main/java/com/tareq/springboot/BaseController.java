package com.tareq.springboot;

import com.tareq.springboot.security.AppUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseController {
    public AppUser getCurrentUser(){
        return (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
