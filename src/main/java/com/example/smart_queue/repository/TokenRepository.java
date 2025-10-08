package com.example.smart_queue.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.smart_queue.model.Token;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {
    List<Token> findByServiceAndServedFalseOrderBySequenceAsc(String service);
    Optional<Token> findTopByServiceOrderBySequenceDesc(String service);
    long countByServiceAndServedFalse(String service);
    Optional<Token> findByTokenNo(String tokenNo);
}
