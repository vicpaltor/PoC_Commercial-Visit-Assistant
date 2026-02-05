package com.bbva.apx.commercialvisit.application.ports.output;

import com.bbva.apx.commercialvisit.domain.model.ClientFinancialProfile;

public interface GenerativeAIAdapter {
    
    String generateFinancialSummary(ClientFinancialProfile anonymizedProfile);
}