package com.example.smart_queue.repository;

import com.example.smart_queue.model.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TokenRepository extends MongoRepository<Token, String> {
    Token findByPosition(int position);  // custom method
}
