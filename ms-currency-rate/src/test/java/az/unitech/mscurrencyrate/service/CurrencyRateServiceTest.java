package az.unitech.mscurrencyrate.service;

import az.unitech.mscurrencyrate.entity.CurrencyRateEntity;
import az.unitech.mscurrencyrate.exception.CurrencyRateNotFoundException;
import az.unitech.mscurrencyrate.mapper.CurrencyRateMapper;
import az.unitech.mscurrencyrate.model.CurrencyRateDto;
import az.unitech.mscurrencyrate.repository.CurrencyRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyRateServiceTest {

    @Mock
    private CurrencyRateRepository currencyRateRepository;
    @Mock
    private CurrencyRateMapper currencyRateMapper;

    private CurrencyRateService currencyRateService;

    @BeforeEach
    void setUp() {
        currencyRateService = new CurrencyRateService(currencyRateRepository, currencyRateMapper);
    }

    @Test
    void shouldSaveCurrencyRateSuccessfully() {
        var currencyRateDto = new CurrencyRateDto();
        currencyRateDto.setExchangeRate(BigDecimal.valueOf(1.70));
        currencyRateDto.setTargetCurrency("AZN");
        currencyRateDto.setSourceCurrency("USD");
        currencyRateDto.setUpdateTime(LocalDateTime.now());

        var currencyRateEntity = new CurrencyRateEntity();
        currencyRateEntity.setExchangeRate(BigDecimal.valueOf(1.70));
        currencyRateEntity.setTargetCurrency("AZN");
        currencyRateEntity.setSourceCurrency("USD");
        currencyRateEntity.setUpdateTime(LocalDateTime.now());

        when(currencyRateMapper.toCurrencyRateEntity(any())).thenReturn(currencyRateEntity);
        currencyRateService.saveCurrencyRate(currencyRateDto);

        verify(currencyRateRepository).save(any(CurrencyRateEntity.class));
        verify(currencyRateMapper).toCurrencyRateEntity(any(CurrencyRateDto.class));
    }

    @Test
    void getCurrencyRate_dataNotFound() {
        String sourceCurrency = "USD";
        String targetCurrency = "EUR";

        when(currencyRateRepository.findBySourceCurrencyAndTargetCurrencyIgnoreCase(any(), any()))
                .thenReturn(Optional.empty());

        assertThrows(CurrencyRateNotFoundException.class,
                () -> currencyRateService.getCurrencyRate(sourceCurrency, targetCurrency));
    }

}