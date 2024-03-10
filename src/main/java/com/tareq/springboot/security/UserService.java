package com.tareq.springboot.security;

import com.tareq.springboot.errors.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService /* Because it is responsible about User Services */ {
    @Autowired
    private UserRepo userRepo;

    @Bean
    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = userRepo.findByEmail(email);
        if(user == null) {
            throw new NotFoundException("No User exist with the passed email in database ^_^");
        }
        return user;
    }

    public void save(AppUser user){
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        this.userRepo.save(user);
    }

    public List<AppUser> findAll(){
        return userRepo.findAll();
    }

    public AppUser findUserByUsername(String username){
        return userRepo.findAppUserByEmail(username);
    }
}
