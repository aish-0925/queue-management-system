package com.example.smart_queue.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tokens")
public class Token {
    @Id
    private String id;

    @Indexed(unique = true)
    private String tokenNo; // e.g., "A001" or numeric string

    private String service; // optional: which service/queue
    private int sequence; // numeric sequence
    private Instant createdAt;
    private boolean served;
    private Instant servedAt;

    public Token() {}

    public Token(String tokenNo, String service, int sequence) {
        this.tokenNo = tokenNo;
        this.service = service;
        this.sequence = sequence;
        this.createdAt = Instant.now();
        this.served = false;
    }

    // getters and setters
    // (omit for brevity or use Lombok if preferred)
    // ...
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTokenNo() { return tokenNo; }
    public void setTokenNo(String tokenNo) { this.tokenNo = tokenNo; }
    public String getService() { return service; }
    public void setService(String service) { this.service = service; }
    public int getSequence() { return sequence; }
    public void setSequence(int sequence) { this.sequence = sequence; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public boolean isServed() { return served; }
    public void setServed(boolean served) { this.served = served; }
    public Instant getServedAt() { return servedAt; }
    public void setServedAt(Instant servedAt) { this.servedAt = servedAt; }
}
