package com.tareq.springboot.RepoTest;

import com.tareq.springboot.security.AppUser;
import com.tareq.springboot.security.UserRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@SpringBootTest
public class UserRepoTest {
    @Autowired
    private UserRepo userRepo;

    @Test
    public void WhenFindByEmail_ReturnTheUserWithTheSpecificEmail(){
        AppUser user = new AppUser("ahmad@gmail.com", "12345", "ahmad");
        userRepo.save(user);

        AppUser user1 = userRepo.findByEmail(user.getUsername());

        Assertions.assertThat(user1).isNotNull();
        userRepo.delete(user);
    }
    @Test
    public void WhenFindAppUserByEmail_ReturnTheUserWithTheSpecificEmail() {
        AppUser user = new AppUser("ahmad@gmail.com", "12345", "ahmad");
        userRepo.save(user);

        AppUser user1 = userRepo.findAppUserByEmail(user.getEmail());

        Assertions.assertThat(user1).isNotNull();
        userRepo.delete(user);
    }
}
