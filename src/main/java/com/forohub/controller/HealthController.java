package com.forohub.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.HashMap;

@RestController
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");
        status.put("message", "ForoHub API is running");
        status.put("timestamp", java.time.Instant.now().toString());
        return ResponseEntity.ok(status);
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> root() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "¡Bienvenido a ForoHub API!");
        response.put("version", "1.0.0");
        response.put("endpoints", "/api/login, /api/topicos, /health");
        return ResponseEntity.ok(response);
    }
}
