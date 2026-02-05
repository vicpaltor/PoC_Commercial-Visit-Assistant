package com.bbva.apx.commercialvisit.domain.model;

import lombok.Builder;
import lombok.Value;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class ClientFinancialProfile {
    
    String clientId;
    String documentNumber;
    String fullName;
    RiskProfile riskProfile;
    BigDecimal totalAssets;
    BigDecimal monthlyIncome;
    List<String> activeProducts;
    LocalDateTime lastContactDate;
    SegmentType segmentType;
    
    public enum RiskProfile {
        CONSERVADOR,
        MODERADO, 
        AGRESIVO
    }
    
    public enum SegmentType {
        PREMIUM,
        PREFERENTE,
        ESTANDARD
    }
}