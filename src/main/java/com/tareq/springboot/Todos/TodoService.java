package com.tareq.springboot.Todos;

import com.tareq.springboot.errors.ConflictException;
import com.tareq.springboot.errors.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TodoService {

    @Autowired
    private TodoRepo todorepo;

    public List<Todo> findAll() {
        return todorepo.findAll();
    }

    public Todo getById(String id){
        try{
            return (Todo) todorepo.findById(id).get();
        }catch (NoSuchElementException ex){
            throw new NotFoundException(String.format("No Record with the id [%s] found in database!", id));
        }
    }
    public Todo save(Todo todo){
        if(todorepo.findByTitle(todo.getTitle())!=null){
            throw new ConflictException(String.format("There exist an object with the same title [%s] in database", todo.getTitle()));
        }
        return (Todo) todorepo.insert(todo);
    }
    public void unSave(String id){
        todorepo.deleteById(id);
    }

}
