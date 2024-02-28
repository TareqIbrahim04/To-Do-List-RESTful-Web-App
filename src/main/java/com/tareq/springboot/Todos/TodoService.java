package com.tareq.springboot.Todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepo todorepo;

    public List<Todo> findAll() {
        return todorepo.findAll();
    }

    public Todo getById(String id){
        return (Todo) todorepo.findById(id).get();
    }
    public Todo save(Todo todo){
        return (Todo) todorepo.insert(todo);
    }
    public void unSave(String id){
        todorepo.deleteById(id);
    }

}
