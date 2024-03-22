package com.tareq.springboot.security;

import com.tareq.springboot.Todos.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
@Component
@Repository
public interface UserRepo extends MongoRepository<AppUser, String> {
    AppUser findByEmail(String email);
    AppUser findAppUserByEmail(String email);
}
