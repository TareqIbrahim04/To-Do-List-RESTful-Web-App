package com.tareq.springboot.Todos;

import com.tareq.springboot.BaseController;
import com.tareq.springboot.errors.ConflictException;
import com.tareq.springboot.errors.NotFoundException;
import com.tareq.springboot.security.AppUser;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TodoService{

    @Autowired
    private TodoRepo todorepo;
    @Autowired
    private BaseController baseController;
    public List<Todo> findAll() {
        return todorepo.findAll();
    }
    public List<Todo> findByUserId(String userId){
        return todorepo.findByUserId(userId);
    }

    public Todo getById(String id){
        Todo todo = todorepo.findByIdAndUserId(id,baseController.getCurrentUser().getId());
        if(todo!=null){
            return (Todo) todorepo.findByIdAndUserId(id,baseController.getCurrentUser().getId());
        }else{
            throw new NotFoundException(String.format("No Record with the id [%s] found in database!", id));
        }
    }
    public Todo save(Todo todo){
        Todo databaseTodo = todorepo.findByTitle(todo.getTitle());
        if(databaseTodo != null && databaseTodo.getUserId().equals(baseController.getCurrentUser().getId())){
            throw new ConflictException(String.format("There exist an object with the same title [%s] in database", todo.getTitle()));
        }
        return (Todo) todorepo.insert(todo);
    }
    public void unSave(String id){
        Todo todo =  todorepo.findByIdAndUserId(id, baseController.getCurrentUser().getId());
        if(todo == null){
            throw new NotFoundException(String.format("No Record with the id [%s] found in database!", id));
        }
        todorepo.deleteByIdAndUserId(id, baseController.getCurrentUser().getId());
    }

    public Todo update(String id, Todo todo) {
        Todo todo1 = todorepo.findByIdAndUserId(id, baseController.getCurrentUser().getId());
        if(todo1 == null){
            throw new NotFoundException(String.format("No To-do with this id [%s] found in database!", id));
        }
        if(todo.getTitle() != null) todo1.setTitle(todo.getTitle());
        if(todo.getDescription() != null) todo1.setDescription(todo.getDescription());
        if(!todo.getStatus().equals("unDone")) todo1.setStatus(todo.getStatus());
        todorepo.save(todo1);
        return todo1;
    }
}
