package com.tareq.springboot.util;

import com.tareq.springboot.security.AppUser;
import com.tareq.springboot.security.UserRepo;
import com.tareq.springboot.security.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FirstTimeRunner implements CommandLineRunner { // @Component class + implements CommandLineRunner = class that automatically run things (I want) first
    private final Log logger = LogFactory.getLog(FirstTimeRunner.class);
    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        //Check if we have users
        // if no users exist, create some users
        if(userService.findAll().isEmpty()){
            logger.info("No Users found. creating some users");
            AppUser user = new AppUser("tareq@gmail.com","password","Tareq");
            userService.save(user);
        }

    }
}
