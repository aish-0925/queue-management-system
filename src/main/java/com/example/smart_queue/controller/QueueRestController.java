package com.example.smart_queue.controller;

import com.example.smart_queue.dto.TokenResponse;
import com.example.smart_queue.model.Token;
import com.example.smart_queue.service.QueueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/queue")
public class QueueRestController {

    private final QueueService queueService;

    public QueueRestController(QueueService queueService) {
        this.queueService = queueService;
    }

    // create/generate token for a service (service param optional: default "A")
    @PostMapping("/token")
    public ResponseEntity<TokenResponse> createToken(@RequestParam(defaultValue = "A") String service) {
        Token token = queueService.generateToken(service);
        TokenResponse resp = new TokenResponse(token.getTokenNo(), token.getService(), token.getSequence());
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/position/{tokenNo}")
    public ResponseEntity<Object> getPosition(@PathVariable String tokenNo) {
        long pos = queueService.getPositionForToken(tokenNo);
        if (pos < 0) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(java.util.Map.of("token", tokenNo, "position", pos));
    }
}
