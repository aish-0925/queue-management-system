package com.example.smart_queue.service;

import com.example.smart_queue.model.Token;
import com.example.smart_queue.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    // Generate new token
    public Token generateToken(String service) {
        int sequence = tokenRepository.findTopByServiceOrderBySequenceDesc(service)
                .map(t -> t.getSequence() + 1)
                .orElse(1);

        String tokenNo = service.substring(0, 1).toUpperCase() + String.format("%03d", sequence);
        Token token = new Token(tokenNo, service, sequence);
        return tokenRepository.save(token);
    }

    // Fetch all pending tokens
    public List<Token> getPendingTokens(String service) {
        return tokenRepository.findAll()
                .stream()
                .filter(t -> !t.isServed() && t.getService().equals(service))
                .toList();
    }

    // Mark token as served
    public Optional<Token> markAsServed(String tokenNo) {
        Optional<Token> tokenOpt = tokenRepository.findByTokenNo(tokenNo);
        tokenOpt.ifPresent(token -> {
            token.setServed(true);
            token.setServedAt(Instant.now());
            tokenRepository.save(token);
        });
        return tokenOpt;
    }

    public List<Token> getAllTokens() {
        return tokenRepository.findAll();
    }
}
