package com.bbva.apx.commercialvisit.infrastructure.outbound.repository.h2;

import com.bbva.apx.commercialvisit.application.ports.output.ClientFinancialProfileRepository;
import com.bbva.apx.commercialvisit.domain.model.ClientFinancialProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Component
public class ClientFinancialProfileH2Adapter implements ClientFinancialProfileRepository {
    
    @Override
    public Optional<ClientFinancialProfile> findByClientId(String clientId) {
        log.debug("Searching client profile for clientId: {}", clientId);
        
        if ("12345678-1234-1234-1234-123456789012".equals(clientId)) {
            ClientFinancialProfile profile = ClientFinancialProfile.builder()
                    .clientId(clientId)
                    .documentNumber("12345678Z")
                    .fullName("Juan Pérez García")
                    .riskProfile(ClientFinancialProfile.RiskProfile.MODERADO)
                    .totalAssets(new BigDecimal("150000.00"))
                    .monthlyIncome(new BigDecimal("5000.00"))
                    .activeProducts(Arrays.asList("CUENTA_CORRIENTE", "TARJETA_CREDITO", "HIPOTECA"))
                    .lastContactDate(LocalDateTime.now().minusDays(5))
                    .segmentType(ClientFinancialProfile.SegmentType.PREFERENTE)
                    .build();
            
            log.debug("Profile found for clientId: {}", clientId);
            return Optional.of(profile);
        }
        
        if ("87654321-4321-4321-4321-210987654321".equals(clientId)) {
            ClientFinancialProfile profile = ClientFinancialProfile.builder()
                    .clientId(clientId)
                    .documentNumber("87654321X")
                    .fullName("María Rodríguez López")
                    .riskProfile(ClientFinancialProfile.RiskProfile.CONSERVADOR)
                    .totalAssets(new BigDecimal("80000.00"))
                    .monthlyIncome(new BigDecimal("3500.00"))
                    .activeProducts(Arrays.asList("CUENTA_AHORRO", "FONDO_INVERSION"))
                    .lastContactDate(LocalDateTime.now().minusDays(2))
                    .segmentType(ClientFinancialProfile.SegmentType.ESTANDARD)
                    .build();
            
            log.debug("Profile found for clientId: {}", clientId);
            return Optional.of(profile);
        }
        
        log.warn("No profile found for clientId: {}", clientId);
        return Optional.empty();
    }
}