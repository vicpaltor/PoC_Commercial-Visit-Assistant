package com.bbva.apx.commercialvisit.application.ports.output;

import com.bbva.apx.commercialvisit.domain.model.ClientFinancialProfile;

import java.util.Optional;

public interface ClientFinancialProfileRepository {
    
    Optional<ClientFinancialProfile> findByClientId(String clientId);
}