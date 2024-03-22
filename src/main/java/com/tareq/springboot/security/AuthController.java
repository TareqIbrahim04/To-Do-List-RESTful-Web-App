package com.tareq.springboot.security;

import com.tareq.springboot.Todos.TodoService;
import com.tareq.springboot.errors.ArgumentException;
import com.tareq.springboot.errors.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping(value = {"/login", "/login/"})
    public JwtResponse signIn(@RequestBody SignInRequest signInRequest){
        // by this final we can tell what kind of authentication we want -> UsernamePassword..... // and insure if the user logged in
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userService.loadUserByUsername(signInRequest.getUsername());
        String token = tokenUtil.generateToken(userDetails);
        return new JwtResponse(token);
    }
    @PostMapping(value = {"/signup","/signup/"})
    public String signUp(@RequestBody SignUpRequest signUpRequest){
        String username = signUpRequest.getUsername();
        userService.findUserByUsername(signUpRequest.getUsername());
        AppUser newUser = new AppUser(signUpRequest.getUsername(),signUpRequest.getPassword(),signUpRequest.getName());
        userService.save(newUser);
        return "Account Created! please login";
    }

}
