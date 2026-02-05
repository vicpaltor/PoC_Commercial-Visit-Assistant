package com.bbva.apx.commercialvisit.application.usecases;

import com.bbva.apx.commercialvisit.application.ports.input.GetVisitBriefingUseCase;
import com.bbva.apx.commercialvisit.application.ports.output.ClientFinancialProfileRepository;
import com.bbva.apx.commercialvisit.application.ports.output.GenerativeAIAdapter;
import com.bbva.apx.commercialvisit.domain.model.ClientFinancialProfile;
import com.bbva.apx.commercialvisit.domain.service.DataAnonymizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetVisitBriefingUseCaseImpl implements GetVisitBriefingUseCase {
    
    private final ClientFinancialProfileRepository repository;
    private final GenerativeAIAdapter aiAdapter;
    private final DataAnonymizer dataAnonymizer;
    
    @Override
    public Optional<String> generateVisitBriefing(String clientId) {
        log.info("Generating visit briefing for clientId: {}", clientId);
        
        Optional<ClientFinancialProfile> profileOpt = repository.findByClientId(clientId);
        if (profileOpt.isEmpty()) {
            log.warn("Client profile not found for clientId: {}", clientId);
            return Optional.empty();
        }
        
        ClientFinancialProfile profile = profileOpt.get();
        log.debug("Found client profile: {}", profile.getClientId());
        
        ClientFinancialProfile anonymizedProfile = dataAnonymizer.anonymizeSensitiveData(profile);
        log.debug("Profile anonymized successfully");
        
        try {
            String briefing = aiAdapter.generateFinancialSummary(anonymizedProfile);
            log.info("Briefing generated successfully for clientId: {}", clientId);
            return Optional.of(briefing);
            
        } catch (Exception e) {
            log.error("Error generating briefing for clientId: {}", clientId, e);
            throw new RuntimeException("Error generating financial briefing", e);
        }
    }
}