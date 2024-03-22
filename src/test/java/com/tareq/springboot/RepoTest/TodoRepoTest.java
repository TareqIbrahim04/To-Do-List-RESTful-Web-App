package com.tareq.springboot.RepoTest;

import com.tareq.springboot.Todos.Todo;
import com.tareq.springboot.Todos.TodoRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;

@SpringBootTest
public class TodoRepoTest {
    @Autowired
    private TodoRepo todoRepo;

    @Test
    public void whenFindByTitle_ReturnTodoWithSpecificTitle(){
        Todo todo = new Todo("1","Hi","1st task");
        todoRepo.save(todo);

        Todo todoReturn = todoRepo.findByTitle(todo.getTitle());

        Assertions.assertThat(todoReturn).isNotNull();
    }

    @Test
    public void whenFindByIdAndUserId_ReturnTodoWithSpecificIdAndUserId(){
        Todo todo = new Todo("1","Hi","1st task");
        todo.setUserId("1");
        todoRepo.save(todo);

        Todo todoReturn = todoRepo.findByIdAndUserId(todo.getId(), todo.getUserId());

        Assertions.assertThat(todoReturn).isNotNull();
    }

    @Test
    public void whendeleteByIdAndUserId_TodoWithSpecificIdAndUserIdIsEmpty(){
        Todo todo = new Todo("1","Hi","1st task");
        todo.setUserId("1");
        todoRepo.save(todo);

        todoRepo.deleteByIdAndUserId(todo.getId(), todo.getUserId());
        Todo todoReturn = todoRepo.findByIdAndUserId(todo.getId(), todo.getUserId());

        Assertions.assertThat(todoReturn).isNull();
    }

    @Test
    public void whenfindByUserId_ReturnListTodoWithSpecificUserId(){
        Todo todo = new Todo("1","Hi","1st task");
        todo.setUserId("1");
        Todo todo1 = new Todo("2","Hello","2st task");
        todo1.setUserId("1");
        todoRepo.save(todo);
        todoRepo.save(todo1);

        List<Todo> todoReturn = todoRepo.findByUserId(todo.getUserId());

        Assertions.assertThat(todoReturn).hasSize(2);
        Assertions.assertThat(todoReturn).isNotEmpty();
    }


}
