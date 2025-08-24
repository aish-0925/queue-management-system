package com.example.smart_queue.controller;

import com.example.smart_queue.model.Token;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    // When client sends message to /app/newToken
    @MessageMapping("/newToken")
    @SendTo("/topic/queue")
    public Token broadcastNewToken(Token token) {
        System.out.println("Broadcasting new token: " + token.getUserName());
        return token; // This will be sent to all subscribers of /topic/queue
    }

    // Example: when token is served
    @MessageMapping("/serveToken")
    @SendTo("/topic/queue")
    public Token broadcastServedToken(Token token) {
        token.setStatus("SERVED");
        System.out.println("Token served: " + token.getUserName());
        return token;
    }
}
