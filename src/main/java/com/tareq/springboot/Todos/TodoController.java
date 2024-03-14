package com.tareq.springboot.Todos;

import com.tareq.springboot.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RequestMapping(value = "/api/v1/todos")
@RestController
public class TodoController extends BaseController {

    @Autowired
    private TodoService todoService;

    @GetMapping(value =  "/{idx}")
    public ResponseEntity<Todo> specialTodoList(@PathVariable String idx){
        Todo result = todoService.getById(idx);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping(value = {"","/"})
    public ResponseEntity<List<Todo>> AllTodoList(){
        List<Todo> result = todoService.findByUserId(getCurrentUser().getId());
        return new ResponseEntity<>(result , HttpStatus.OK);
    }
    @PostMapping(value = {"","/"})
    public ResponseEntity<Todo> creatNewTodo(@Valid @RequestBody Todo todo){
        todo.setUserId(getCurrentUser().getId());
        Todo result = todoService.save(todo);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String id){
        todoService.unSave(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable String id,@RequestBody Todo todo){
        Todo result = todoService.update(id, todo);
        return new ResponseEntity<>(result ,HttpStatus.OK);
    }
}
