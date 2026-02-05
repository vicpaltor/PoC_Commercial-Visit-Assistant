package com.bbva.apx.commercialvisit.infrastructure.inbound.controller;

import com.bbva.apx.commercialvisit.application.ports.input.GetVisitBriefingUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/visits")
@RequiredArgsConstructor
public class VisitController {
    
    private final GetVisitBriefingUseCase getVisitBriefingUseCase;
    
    @GetMapping("/briefing/{clientId}")
    public ResponseEntity<?> getVisitBriefing(@PathVariable String clientId) {
        log.info("Received request for visit briefing for clientId: {}", clientId);
        
        if (!isValidUUID(clientId)) {
            return ResponseEntity.badRequest().body(createErrorResponse("INVALID_CLIENT_ID", 
                "El clientId debe ser un UUID válido", LocalDateTime.now()));
        }
        
        return getVisitBriefingUseCase.generateVisitBriefing(clientId)
            .map(briefing -> {
                Map<String, Object> response = new HashMap<>();
                response.put("clientId", clientId);
                response.put("briefing", briefing);
                response.put("generatedAt", LocalDateTime.now());
                return ResponseEntity.ok(response);
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    private boolean isValidUUID(String clientId) {
        try {
            UUID.fromString(clientId);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    
    private Map<String, Object> createErrorResponse(String code, String message, LocalDateTime timestamp) {
        Map<String, Object> error = new HashMap<>();
        error.put("code", code);
        error.put("message", message);
        error.put("timestamp", timestamp);
        return error;
    }
}