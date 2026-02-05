package com.bbva.apx.commercialvisit.application.usecases;

import com.bbva.apx.commercialvisit.application.ports.output.ClientFinancialProfileRepository;
import com.bbva.apx.commercialvisit.application.ports.output.GenerativeAIAdapter;
import com.bbva.apx.commercialvisit.domain.model.ClientFinancialProfile;
import com.bbva.apx.commercialvisit.domain.service.DataAnonymizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetVisitBriefingUseCaseImplTest {

    @Mock
    private ClientFinancialProfileRepository repository;

    @Mock
    private GenerativeAIAdapter aiAdapter;

    @Mock
    private DataAnonymizer dataAnonymizer;

    @InjectMocks
    private GetVisitBriefingUseCaseImpl useCase;

    private ClientFinancialProfile testProfile;
    private ClientFinancialProfile anonymizedProfile;
    private String testClientId;
    private String expectedBriefing;

    @BeforeEach
    void setUp() {
        testClientId = "12345678-1234-1234-1234-123456789012";
        expectedBriefing = "Cliente con alto potencial de inversión en productos de patrimonio.";

        testProfile = ClientFinancialProfile.builder()
                .clientId(testClientId)
                .documentNumber("12345678Z")
                .fullName("Juan Pérez García")
                .riskProfile(ClientFinancialProfile.RiskProfile.MODERADO)
                .totalAssets(new BigDecimal("150000.00"))
                .monthlyIncome(new BigDecimal("5000.00"))
                .activeProducts(Arrays.asList("CUENTA_CORRIENTE", "TARJETA_CREDITO"))
                .lastContactDate(LocalDateTime.now())
                .segmentType(ClientFinancialProfile.SegmentType.PREFERENTE)
                .build();

        anonymizedProfile = ClientFinancialProfile.builder()
                .clientId(testClientId)
                .documentNumber("12***Z")
                .fullName("J***n P***z G***a")
                .riskProfile(ClientFinancialProfile.RiskProfile.MODERADO)
                .totalAssets(new BigDecimal("150000.00"))
                .monthlyIncome(new BigDecimal("5000.00"))
                .activeProducts(Arrays.asList("CUENTA_CORRIENTE", "TARJETA_CREDITO"))
                .lastContactDate(LocalDateTime.now())
                .segmentType(ClientFinancialProfile.SegmentType.PREFERENTE)
                .build();
    }

    @Test
    void generateVisitBriefing_WhenClientExists_ShouldReturnBriefing() {
        when(repository.findByClientId(testClientId)).thenReturn(Optional.of(testProfile));
        when(dataAnonymizer.anonymizeSensitiveData(testProfile)).thenReturn(anonymizedProfile);
        when(aiAdapter.generateFinancialSummary(anonymizedProfile)).thenReturn(expectedBriefing);

        Optional<String> result = useCase.generateVisitBriefing(testClientId);

        assertTrue(result.isPresent());
        assertEquals(expectedBriefing, result.get());

        verify(repository).findByClientId(testClientId);
        verify(dataAnonymizer).anonymizeSensitiveData(testProfile);
        verify(aiAdapter).generateFinancialSummary(anonymizedProfile);
    }

    @Test
    void generateVisitBriefing_WhenClientNotFound_ShouldReturnEmpty() {
        when(repository.findByClientId(testClientId)).thenReturn(Optional.empty());

        Optional<String> result = useCase.generateVisitBriefing(testClientId);

        assertFalse(result.isPresent());

        verify(repository).findByClientId(testClientId);
        verify(dataAnonymizer, never()).anonymizeSensitiveData(any());
        verify(aiAdapter, never()).generateFinancialSummary(any());
    }

    @Test
    void generateVisitBriefing_WhenAIAdapterThrowsException_ShouldPropagateException() {
        when(repository.findByClientId(testClientId)).thenReturn(Optional.of(testProfile));
        when(dataAnonymizer.anonymizeSensitiveData(testProfile)).thenReturn(anonymizedProfile);
        when(aiAdapter.generateFinancialSummary(anonymizedProfile))
                .thenThrow(new RuntimeException("AI Service unavailable"));

        assertThrows(RuntimeException.class, () -> useCase.generateVisitBriefing(testClientId));

        verify(repository).findByClientId(testClientId);
        verify(dataAnonymizer).anonymizeSensitiveData(testProfile);
        verify(aiAdapter).generateFinancialSummary(anonymizedProfile);
    }
}