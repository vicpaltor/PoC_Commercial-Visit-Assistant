package com.bbva.apx.commercialvisit.infrastructure.inbound.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Mapper
public interface VisitMapper {
    
    VisitMapper INSTANCE = Mappers.getMapper(VisitMapper.class);
    
    default Map<String, Object> toBriefingResponse(String clientId, String briefing) {
        Map<String, Object> response = new HashMap<>();
        response.put("clientId", clientId);
        response.put("briefing", briefing);
        response.put("generatedAt", LocalDateTime.now());
        return response;
    }
    
    default Map<String, Object> toErrorResponse(String code, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("code", code);
        error.put("message", message);
        error.put("timestamp", LocalDateTime.now());
        return error;
    }
}