package com.bbva.apx.commercialvisit.config;

import com.bbva.apx.commercialvisit.domain.service.DataAnonymizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public DataAnonymizer dataAnonymizer() {
        return new DataAnonymizer();
    }
}