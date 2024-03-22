package com.tareq.springboot.SreviceTest;

import com.tareq.springboot.BaseController;
import com.tareq.springboot.Todos.Todo;
import com.tareq.springboot.Todos.TodoRepo;
import com.tareq.springboot.Todos.TodoService;
import com.tareq.springboot.errors.NotFoundException;
import com.tareq.springboot.security.AppUser;
import com.tareq.springboot.security.UserRepo;
import com.tareq.springboot.security.UserService;
import net.bytebuddy.pool.TypePool;
import org.apache.catalina.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TodoServiceTest {
    @Mock
    private TodoRepo todoRepo;

    @Mock
    private BaseController baseController;

    @InjectMocks
    private TodoService todoService;

    @Test
    public void WhenGetById_ReturnTodo(){
        Todo todo = new Todo("2","world","hahaha");
        todo.setUserId("2");
        AppUser user = new AppUser("omar@gmail.com","12345","omar");
        user.setId("2");

        when(todoRepo.findByIdAndUserId("2","2")).thenReturn(todo);
        when(baseController.getCurrentUser()).thenReturn(user);

        Todo todoReturn = todoService.getById(todo.getId());

        Assertions.assertThat(todoReturn).isNotNull();

    }

    @Test
    public void WhenGetByIdNotFound_ReturnNotFoundException(){
        AppUser user = new AppUser("omar@gmail.com","12345","omar");
        user.setId("2");

        when(baseController.getCurrentUser()).thenReturn(user);
        when(todoRepo.findByIdAndUserId("nonExistedId","2")).thenReturn(null);

        Assertions.assertThatThrownBy(() -> todoService.getById("nonExistedId"))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("No Record with the id [nonExistedId] found in database!");

    }
    @Test
    public void WhenUnSave_verifyItsDone(){
        Todo todo = new Todo("2","world","hahaha");
        todo.setUserId("2");
        AppUser user = new AppUser("omar@gmail.com","12345","omar");
        user.setId("2");

        when(baseController.getCurrentUser()).thenReturn(user);
        when(todoRepo.findByIdAndUserId("2","2")).thenReturn(todo);

        assertAll(() -> todoService.unSave("2"));
    }

    @Test
    public void WhenUnSaveNotFound_ReturnNotFoundException(){
        AppUser user = new AppUser("omar@gmail.com","12345","omar");
        user.setId("2");

        when(baseController.getCurrentUser()).thenReturn(user);
        when(todoRepo.findByIdAndUserId("nonExistedId","2")).thenReturn(null);

        Assertions.assertThatThrownBy(() -> todoService.unSave("nonExistedId"))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("No Record with the id [nonExistedId] found in database!");

    }

    @Test
    public void WhenUpdate_ReturnUpdatedTodo(){
        Todo todo = new Todo("2","world","hahaha");
        todo.setUserId("2");
        Todo updatedTodo = new Todo("2","ggg","hhh");
        updatedTodo.setUserId("2");
        updatedTodo.setStatus("Done");
        AppUser user = new AppUser("omar@gmail.com","12345","omar");
        user.setId("2");

        when(todoRepo.findByIdAndUserId("2","2")).thenReturn(todo);
        when(baseController.getCurrentUser()).thenReturn(user);

        Todo todoReturn = todoService.update(todo.getId(), updatedTodo);

        Assertions.assertThat(todoReturn).isNotNull();
        Assertions.assertThat(todoReturn.getTitle()).isEqualTo(updatedTodo.getTitle());
        Assertions.assertThat(todoReturn.getDescription()).isEqualTo(updatedTodo.getDescription());
        Assertions.assertThat(todoReturn.getStatus()).isEqualTo(updatedTodo.getStatus());
    }

    @Test
    public void WhenUpdateNotFound_ReturnNotFoundException(){
        AppUser user = new AppUser("omar@gmail.com","12345","omar");
        user.setId("2");

        when(baseController.getCurrentUser()).thenReturn(user);
        when(todoRepo.findByIdAndUserId("nonExistedId","2")).thenReturn(null);

        Assertions.assertThatThrownBy(() -> todoService.update("nonExistedId", null))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("No To-do with this id [nonExistedId] found in database!");

    }
}
