package com.example.smart_queue.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.smart_queue.model.ServiceEntity;

@Repository
public interface ServiceRepository extends MongoRepository<ServiceEntity, String> {
    Optional<ServiceEntity> findByCode(String code);
}
