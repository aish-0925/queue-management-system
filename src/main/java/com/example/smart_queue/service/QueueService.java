package com.example.smart_queue.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.smart_queue.model.Token;
import com.example.smart_queue.repository.TokenRepository;

@Service
public class QueueService {
    private final TokenRepository tokenRepository;

    public QueueService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token createToken(String userName) {
        int position = (int) tokenRepository.count() + 1;
        Token token = new Token(userName, position, "WAITING");
        return tokenRepository.save(token);
    }

    public List<Token> getAllTokens() {
        return tokenRepository.findAll();
    }

    public void serveNextToken() {
        List<Token> tokens = tokenRepository.findAll();
        if (!tokens.isEmpty()) {
            Token first = tokens.get(0);
            first.setStatus("SERVED");
            tokenRepository.save(first);
        }
    }
}
