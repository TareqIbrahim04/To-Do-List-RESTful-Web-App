package com.tareq.springboot.SreviceTest;

import com.tareq.springboot.errors.ArgumentException;
import com.tareq.springboot.errors.NotFoundException;
import com.tareq.springboot.security.AppUser;
import com.tareq.springboot.security.UserRepo;
import com.tareq.springboot.security.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    @Test
    public void whenLoadUserByUsername_returnUser(){
        AppUser user = new AppUser("omar@gmail.com","12345","omar");
        user.setId("2");

        when(userRepo.findByEmail(user.getUsername())).thenReturn(user);

        AppUser userReturn = (AppUser) userService.loadUserByUsername(user.getUsername());

        Assertions.assertThat(userReturn).isNotNull();
    }

    @Test
    public void whenLoadUserByUsernameNotFound_throwNotFoundException(){
        String usernameNonExisted = "nonExisted";
        when(userRepo.findByEmail(usernameNonExisted)).thenReturn(null);

        Assertions.assertThatThrownBy(() -> userService.loadUserByUsername(usernameNonExisted))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("No User exist with the passed email in database ^_^");
    }

    @Test
    public void whenSave_verifyDone(){
        AppUser user = new AppUser("omar@gmail.com","12345","omar");
        user.setId("2");

        when(userRepo.save(user)).thenReturn(user);

        assertAll(() -> userService.save(user));
    }

    @Test
    public void whenFindAll_returnAllUsers(){
        AppUser user = new AppUser("omar@gmail.com","12345","omar");
        user.setId("2");
        AppUser user1 = new AppUser("uuu@gmail.com","12345","uuu");
        user.setId("3");
        AppUser user2 = new AppUser("jjj@gmail.com","12345","jjj");
        user.setId("1");

        when(userRepo.findAll()).thenReturn(Arrays.asList(user,user1,user2));

        List<AppUser> userReturn = userService.findAll();

        Assertions.assertThat(userReturn).hasSize(3);
        Assertions.assertThat(userReturn.get(0)).isNotNull();
        Assertions.assertThat(userReturn.get(1)).isNotNull();
        Assertions.assertThat(userReturn.get(2)).isNotNull();
    }

    @Test
    public void whenFindUserByUsername_verifyDone(){
        String nonExistedUser = "nonExistedUser";
        when(userRepo.findAppUserByEmail(nonExistedUser)).thenReturn(null);

        assertAll(() -> userService.findUserByUsername(nonExistedUser));
    }
    @Test
    public void whenFindUserByUsernameExisted_throwArgumentException(){
        AppUser user = new AppUser("omar@gmail.com","12345","omar");
        user.setId("2");

        when(userRepo.findAppUserByEmail(user.getUsername())).thenReturn(user);

        Assertions.assertThatThrownBy(() -> userService.findUserByUsername(user.getUsername()))
                .isInstanceOf(ArgumentException.class)
                .hasMessageContaining("This User with this name is already exist!");
    }
}
