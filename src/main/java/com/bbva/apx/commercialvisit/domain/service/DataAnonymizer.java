package com.bbva.apx.commercialvisit.domain.service;

import com.bbva.apx.commercialvisit.domain.model.ClientFinancialProfile;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

@Slf4j
public class DataAnonymizer {
    
    private static final Pattern DOCUMENT_PATTERN = Pattern.compile("\\d{8}[A-HJ-NP-TV-Z]");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    
    public ClientFinancialProfile anonymizeSensitiveData(ClientFinancialProfile profile) {
        log.debug("Anonymizing sensitive data for clientId: {}", profile.getClientId());
        
        return ClientFinancialProfile.builder()
                .clientId(profile.getClientId())
                .documentNumber(anonymizeDocument(profile.getDocumentNumber()))
                .fullName(anonymizeName(profile.getFullName()))
                .riskProfile(profile.getRiskProfile())
                .totalAssets(profile.getTotalAssets())
                .monthlyIncome(profile.getMonthlyIncome())
                .activeProducts(profile.getActiveProducts())
                .lastContactDate(profile.getLastContactDate())
                .segmentType(profile.getSegmentType())
                .build();
    }
    
    private String anonymizeDocument(String document) {
        if (document == null || document.length() < 4) {
            return "***";
        }
        return document.substring(0, 2) + "***" + document.substring(document.length() - 1);
    }
    
    private String anonymizeName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "ANONYMIZED";
        }
        
        String[] parts = name.trim().split("\\s+");
        StringBuilder anonymized = new StringBuilder();
        
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].length() > 2) {
                anonymized.append(parts[i].charAt(0))
                          .append("***")
                          .append(parts[i].charAt(parts[i].length() - 1));
            } else {
                anonymized.append("***");
            }
            
            if (i < parts.length - 1) {
                anonymized.append(" ");
            }
        }
        
        return anonymized.toString();
    }
}