package com.example.smart_queue.dto;

public class TokenResponse {
    private String tokenNo;
    private String service;
    private int sequence;

    public TokenResponse() {}

    public TokenResponse(String tokenNo, String service, int sequence) {
        this.tokenNo = tokenNo;
        this.service = service;
        this.sequence = sequence;
    }

    // getters and setters
    public String getTokenNo() { return tokenNo; }
    public void setTokenNo(String tokenNo) { this.tokenNo = tokenNo; }
    public String getService() { return service; }
    public void setService(String service) { this.service = service; }
    public int getSequence() { return sequence; }
    public void setSequence(int sequence) { this.sequence = sequence; }
}
