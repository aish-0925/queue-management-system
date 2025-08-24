package com.example.smart_queue.service;

import com.example.smart_queue.model.Token;
import com.example.smart_queue.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Create new token
    public Token createToken(String userName) {
        Token token = new Token(userName);
        Token savedToken = tokenRepository.save(token);

        // 🔔 Notify all subscribers about the new token
        messagingTemplate.convertAndSend("/topic/queue", savedToken);

        return savedToken;
    }

    // Get all tokens
    public List<Token> getAllTokens() {
        return tokenRepository.findAll();
    }

    // Serve token
    public Token serveToken(int position) {
    Token token = tokenRepository.findByPosition(position);
    if (token == null) {
        throw new RuntimeException("Token with position " + position + " not found");
    }
    token.setStatus("SERVED");
    return tokenRepository.save(token);
}

}
