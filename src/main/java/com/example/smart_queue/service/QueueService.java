package com.example.smart_queue.service;

import com.example.smart_queue.model.Token;
import com.example.smart_queue.repository.TokenRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class QueueService {

    private final TokenRepository tokenRepository;
    private final SimpMessagingTemplate messagingTemplate;

    // Simple in-memory counters per service for generating sequential numbers.
    // In distributed setup, you'd use a central sequence generator.
    private final java.util.Map<String, AtomicInteger> counters = new java.util.concurrent.ConcurrentHashMap<>();

    public QueueService(TokenRepository tokenRepository, SimpMessagingTemplate messagingTemplate) {
        this.tokenRepository = tokenRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public Token generateToken(String servicePrefix) {
        counters.putIfAbsent(servicePrefix, new AtomicInteger(0));
        int seq = counters.get(servicePrefix).incrementAndGet();

        // token format: <ServicePrefix><000> -> e.g., A001
        String tokenNo = String.format("%s%03d", servicePrefix.toUpperCase(), seq);
        Token token = new Token(tokenNo, servicePrefix, seq);
        tokenRepository.save(token);

        // Broadcast update to subscribed clients on this service
        broadcastQueueUpdate(servicePrefix);

        return token;
    }

    public List<Token> getPendingTokens(String service) {
        return tokenRepository.findByServiceAndServedFalseOrderBySequenceAsc(service);
    }

    public long getPosition(String tokenNo) {
        return tokenRepository.findByTokenNo(tokenNo)
                .map(token -> tokenRepository.countByServiceAndServedFalse(token.getService())
                        - tokenRepository.findByServiceAndServedFalseOrderBySequenceAsc(token.getService())
                          .stream()
                          .filter(t -> t.getSequence() < token.getSequence())
                          .count()
                ).orElse(-1L);
        // NOTE: we will provide a simpler method below for live position
    }

    // Better: compute position by counting tokens with seq less than this token's seq that are not served
    public long getPositionForToken(String tokenNo) {
        var opt = tokenRepository.findByTokenNo(tokenNo);
        if (opt.isEmpty()) return -1;
        Token token = opt.get();
        return tokenRepository.findByServiceAndServedFalseOrderBySequenceAsc(token.getService())
                .stream()
                .filter(t -> t.getSequence() < token.getSequence())
                .count() + 1; // 1-based position
    }

    public Token serveNext(String service) {
        // find next pending token (lowest seq)
        var pending = tokenRepository.findByServiceAndServedFalseOrderBySequenceAsc(service);
        if (pending.isEmpty()) return null;
        Token next = pending.get(0);
        next.setServed(true);
        next.setServedAt(Instant.now());
        tokenRepository.save(next);
        broadcastQueueUpdate(service);
        return next;
    }

    public void broadcastQueueUpdate(String service) {
        // send summary: pending count, next token, list (optional)
        var pending = tokenRepository.findByServiceAndServedFalseOrderBySequenceAsc(service);
        var summary = new java.util.HashMap<String, Object>();
        summary.put("service", service);
        summary.put("pendingCount", pending.size());
        summary.put("nextToken", pending.isEmpty() ? null : pending.get(0).getTokenNo());
        summary.put("tokens", pending.stream().map(Token::getTokenNo).toList());

        // topic: /topic/queue/{service}
        messagingTemplate.convertAndSend("/topic/queue/" + service, summary);
    }
}
