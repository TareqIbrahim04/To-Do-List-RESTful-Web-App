package com.tareq.springboot.Todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping(value = "/projects/todos")
@RestController
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping(value =  "/{idx}")
    public Todo specialTodoList(@PathVariable int idx){
        return todoService.getData().get(idx-1);
    }
    @GetMapping(value = {"","/"})
    public List<Todo> AllTodoList(){
        return todoService.getData();
    }
    @PostMapping(value = {"","/"})
    public Todo creatNewTodo(@RequestBody Todo todo){
        if(todoService.save(todo)){
            return todo;
        }
        return null;
    }
    @DeleteMapping(value = "/{id}")
    public void deleteTodo(@PathVariable int id){
        todoService.unSave(id);
    }
}
