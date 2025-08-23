package com.example.smart_queue.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.smart_queue.model.Token;

public interface TokenRepository extends MongoRepository<Token, String> {
}
