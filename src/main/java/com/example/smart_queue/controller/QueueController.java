package com.example.smart_queue.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.smart_queue.model.Token;
import com.example.smart_queue.service.QueueService;

@RestController
@RequestMapping("/api/queue")
@CrossOrigin(origins = "*") // allow frontend
public class QueueController {
    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @PostMapping("/create")
    public Token createToken(@RequestParam String userName) {
        return queueService.createToken(userName);
    }

    @GetMapping("/all")
    public List<Token> getAllTokens() {
        return queueService.getAllTokens();
    }

    @PostMapping("/serve")
    public String serveNextToken() {
        queueService.serveNextToken();
        return "Next token served!";
    }
}
