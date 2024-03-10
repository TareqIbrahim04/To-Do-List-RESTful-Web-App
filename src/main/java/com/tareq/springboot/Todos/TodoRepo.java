package com.tareq.springboot.Todos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepo extends MongoRepository<Todo, String> {

    Todo findByTitle(String title);
    Todo findByIdAndUserId(String id, String userId);
    void deleteByIdAndUserId(String id, String userId);
    List<Todo> findByUserId(String userId);
}
