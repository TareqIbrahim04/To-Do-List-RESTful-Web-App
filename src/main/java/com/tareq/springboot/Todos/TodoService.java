package com.tareq.springboot.Todos;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TodoService {
    private List<Todo> data = new ArrayList<Todo>(Arrays.asList( new Todo(1,"First Task", "This is my first task")
            ,new Todo(2,"second Task", "This is my first task")
            ,new Todo(3,"third Task", "This is my first task")
            ,new Todo(4,"forth Task", "This is my first task")
            ,new Todo(5,"fifth Task", "This is my first task")));

    public List<Todo> getData() {
        return data;
    }

    public void setData(List<Todo> data) {
        this.data = data;
    }
    public Boolean save(Todo todo){
        return data.add(todo);
    }
    public void unSave(int id){
        for (Todo x : data){
            if(x.getId() == id-1){
                data.remove(id-1);
            }
        };
    }

}
