package com.example.smart_queue.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "services")
public class ServiceEntity {

    @Id
    private String id;
    private String name;   // e.g., "Consultation"
    private String code;   // e.g., "CONS"
    private int counterCount;

    public ServiceEntity() {}

    public ServiceEntity(String name, String code, int counterCount) {
        this.name = name;
        this.code = code;
        this.counterCount = counterCount;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public int getCounterCount() { return counterCount; }
    public void setCounterCount(int counterCount) { this.counterCount = counterCount; }
}
