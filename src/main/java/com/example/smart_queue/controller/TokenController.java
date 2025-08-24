package com.example.smart_queue.controller;

import com.example.smart_queue.model.Token;
import com.example.smart_queue.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tokens")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    // Create new token
    @PostMapping
    public Token createToken(@RequestParam String userName) {
        return tokenService.createToken(userName);
    }

    // Get all tokens
    @GetMapping
    public List<Token> getAllTokens() {
        return tokenService.getAllTokens();
    }

    // Serve token
    @PutMapping("/tokens/serve/{position}")
public Token serveToken(@PathVariable int position) {  // 👈 make sure it's int
    return tokenService.serveToken(position);
}

}
