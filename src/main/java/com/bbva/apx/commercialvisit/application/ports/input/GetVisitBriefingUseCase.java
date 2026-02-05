package com.bbva.apx.commercialvisit.application.ports.input;

import java.util.Optional;

public interface GetVisitBriefingUseCase {
    
    Optional<String> generateVisitBriefing(String clientId);
}