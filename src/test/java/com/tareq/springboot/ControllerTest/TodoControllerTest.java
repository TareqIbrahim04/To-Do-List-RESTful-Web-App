package com.tareq.springboot.ControllerTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tareq.springboot.BaseController;
import com.tareq.springboot.Todos.Todo;
import com.tareq.springboot.Todos.TodoController;
import com.tareq.springboot.Todos.TodoService;
import com.tareq.springboot.security.AppUser;
import com.tareq.springboot.security.TokenUtil;
import com.tareq.springboot.security.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Arrays;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ContextConfiguration(classes = AppControllerTest.class)
@WebMvcTest(controllers = TodoController.class)
@Import(TodoController.class)
@ExtendWith(MockitoExtension.class)
public class TodoControllerTest{
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mvc;

    @Autowired
    private AppControllerTest appControllerTest;

    @MockBean
    private TodoService todoService;

    @MockBean
    protected UserService userService;

    @MockBean
    protected TokenUtil tokenUtil;

    @MockBean
    private BaseController baseController;

    protected final String USERNAME_FOR_TEST = "khalid@gmail.com";
    protected final String PASSWORD_FOR_TEST = "12345";
    protected final String AUTH_HEADER = "Authorization";

    private String token;


    @Test
    public void whenGetById_returnTodo() throws Exception{
            Todo todo = new Todo("1", "qqqqq", "hello");
            todo.setUserId("1");

            given(todoService.getById(todo.getId())).willReturn(todo);

            ResultActions response = mvc.perform(get("/api/v1/todos/1")
                    .contentType(MediaType.APPLICATION_JSON)
            );


            response.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(todo.getId())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(todo.getTitle())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(todo.getDescription())));

    }

    @Test
    public void whenAllTodoList_returnTodoList() throws Exception{

        Todo todo = new Todo("1","qqqqq","hello");
        Todo todo1 = new Todo("2","eeeee","hi");
        AppUser user = new AppUser("khalid@gmail.com","12345","khalid");
        user.setId("1");
        todo.setUserId("1");
        todo1.setUserId("1");

        given(baseController.getCurrentUser()).willReturn(user);
        given(todoService.findByUserId(user.getId())).willReturn(Arrays.asList(todo,todo1));

        ResultActions response = mvc.perform(get("/api/v1/todos/")
                .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(Arrays.asList(todo,todo1).size())));
    }

    @Test
    public void whenCreateNewTodo_returnTodo() throws Exception{

        Todo todo = new Todo("1","qqqqq","hello");
        AppUser user = new AppUser("khalid@gmail.com","12345","khalid");
        user.setId("1");
        todo.setUserId("1");

        given(baseController.getCurrentUser()).willReturn(user);
        given(todoService.save(todo)).willReturn(todo);

        ResultActions response = mvc.perform(post("/api/v1/todos/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todo))
        );

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(todo.getId())));
    }

    @Test
    public void whenDelete_returnNoContentStatus() throws Exception{
        ResultActions response = mvc.perform(delete("/api/v1/todos/0")
                .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void whenUpdateTodo_returnUpdatedTodo() throws Exception{

        Todo todo = new Todo("1","qqqqq","hello");
        todo.setUserId("1");
        Todo todo1 = new Todo("1","55555","yy");
        todo.setUserId("1");

        given(todoService.update("1", todo)).willReturn(todo1);

        ResultActions response = mvc.perform(put("/api/v1/todos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todo1))
        );

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(todo1.getId())));
    }


}
