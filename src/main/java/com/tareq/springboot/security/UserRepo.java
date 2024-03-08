package com.tareq.springboot.security;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<AppUser, String> {
    AppUser findByEmail(String email);

}
