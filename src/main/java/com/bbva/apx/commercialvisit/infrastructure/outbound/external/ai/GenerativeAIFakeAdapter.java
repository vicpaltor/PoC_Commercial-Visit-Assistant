package com.bbva.apx.commercialvisit.infrastructure.outbound.external.ai;

import com.bbva.apx.commercialvisit.application.ports.output.GenerativeAIAdapter;
import com.bbva.apx.commercialvisit.domain.model.ClientFinancialProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
public class GenerativeAIFakeAdapter implements GenerativeAIAdapter {
    
    private final Random random = new Random();
    
    private final List<String> conservativeBriefings = Arrays.asList(
        "Cliente con perfil conservador ideal para productos de baja volatilidad. Recomendado ofrecer depósitos a plazo y fondos de renta fija.",
        "Perfil de riesgo bajo detectado. Sugerir productos de ahorro garantizado y planes de pensiones conservadores.",
        "Cliente averso al riesgo. Enfocar en productos de tesorería y cuentas remuneradas con bajo riesgo."
    );
    
    private final List<String> moderateBriefings = Arrays.asList(
        "Cliente con perfil moderado recomendable para diversificar entre renta fija y variable. Sugerir fondos mixtos con gestión activa.",
        "Perfil equilibrado detectado. Oportunidad para productos estructurados y planes de inversión diversificados.",
        "Cliente con tolerancia media al riesgo. Ideal para portfolios 60/40 entre acciones y bonos."
    );
    
    private final List<String> aggressiveBriefings = Arrays.asList(
        "Cliente con perfil agresivo y alto potencial de crecimiento. Recomendado productos de renta variable e inversión en mercados emergentes.",
        "Perfil de alto riesgo detectado. Oportunidad para productos alternativos y fondos de alto rendimiento.",
        "Cliente con experiencia inversora. Sugerir productos de capital riesgo y criptoactivos con moderación."
    );
    
    @Override
    public String generateFinancialSummary(ClientFinancialProfile anonymizedProfile) {
        log.debug("Generating AI summary for anonymized profile: {}", anonymizedProfile.getClientId());
        
        String briefing;
        BigDecimal totalAssets = anonymizedProfile.getTotalAssets();
        
        if (totalAssets.compareTo(new BigDecimal("100000")) < 0) {
            briefing = "Cliente con patrimonio moderado. Enfocar en productos de acumulación progresiva y optimización fiscal.";
        } else if (totalAssets.compareTo(new BigDecimal("500000")) < 0) {
            briefing = "Cliente con patrimonio consolidado. Adecuado para gestión patrimonial personalizada y planificación sucesoria.";
        } else {
            briefing = "Cliente High Net Worth. Requerida atención premium con gestión de patrimonios complejos y productos exclusivos.";
        }
        
        List<String> riskBasedBriefings;
        switch (anonymizedProfile.getRiskProfile()) {
            case CONSERVADOR:
                riskBasedBriefings = conservativeBriefings;
                break;
            case MODERADO:
                riskBasedBriefings = moderateBriefings;
                break;
            case AGRESIVO:
                riskBasedBriefings = aggressiveBriefings;
                break;
            default:
                riskBasedBriefings = moderateBriefings;
        }
        
        String riskBriefing = riskBasedBriefings.get(random.nextInt(riskBasedBriefings.size()));
        
        String segmentAdvice;
        switch (anonymizedProfile.getSegmentType()) {
            case PREMIUM:
                segmentAdvice = "Cliente Premium requiere servicio personalizado y productos exclusivos.";
                break;
            case PREFERENTE:
                segmentAdvice = "Cliente Preferente con potencial de upgrading a segmento superior.";
                break;
            default:
                segmentAdvice = "Cliente Estandard con oportunidades de cross-selling.";
        }
        
        return String.format("%s %s %s", briefing, riskBriefing, segmentAdvice);
    }
}