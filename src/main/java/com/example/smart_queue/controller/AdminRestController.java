package com.example.smart_queue.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.smart_queue.model.Token;
import com.example.smart_queue.service.QueueService;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final QueueService queueService;

    public AdminRestController(QueueService queueService) {
        this.queueService = queueService;
    }

    // Serve next token for a service
    @PostMapping("/serve")
    public ResponseEntity<Object> serveNext(@RequestParam(defaultValue = "A") String service) {
        Token next = queueService.serveNext(service);
        if (next == null) {
            return ResponseEntity.ok(java.util.Map.of("message", "No pending tokens"));
        }
        return ResponseEntity.ok(java.util.Map.of("servedToken", next.getTokenNo()));
    }
}
