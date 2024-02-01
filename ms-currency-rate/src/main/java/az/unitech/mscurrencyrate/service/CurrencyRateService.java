package az.unitech.mscurrencyrate.service;

import az.unitech.mscurrencyrate.entity.CurrencyRateEntity;
import az.unitech.mscurrencyrate.exception.CurrencyRateNotFoundException;
import az.unitech.mscurrencyrate.mapper.CurrencyRateMapper;
import az.unitech.mscurrencyrate.model.CurrencyRateDto;
import az.unitech.mscurrencyrate.repository.CurrencyRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyRateService {

    private final CurrencyRateRepository currencyRateRepository;
    private final CurrencyRateMapper mapper;


    public void saveCurrencyRate(CurrencyRateDto currencyRateDto) {
        var currencyRateEntity = mapper.toCurrencyRateEntity(currencyRateDto);
        currencyRateRepository.save(currencyRateEntity);
    }

    @Cacheable(value = "currencyRate", key = "#sourceCurrency + '-' + #targetCurrency")
    public CurrencyRateEntity getCurrencyRate(String sourceCurrency, String targetCurrency) {
        return currencyRateRepository
                .findBySourceCurrencyAndTargetCurrencyIgnoreCase(sourceCurrency, targetCurrency)
                .orElseThrow(CurrencyRateNotFoundException::new);
    }

    @CacheEvict(value = "currencyRate", key = "#sourceCurrency + '-' + #targetCurrency")
    public void evictCurrencyRateCache(String sourceCurrency, String targetCurrency) {

    }

}
