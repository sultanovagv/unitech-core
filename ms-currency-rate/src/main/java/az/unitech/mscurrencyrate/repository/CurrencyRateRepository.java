package az.unitech.mscurrencyrate.repository;

import az.unitech.mscurrencyrate.entity.CurrencyRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRateEntity, Long> {
    Optional<CurrencyRateEntity> findBySourceCurrencyAndTargetCurrencyIgnoreCase(String sourceCurrency,
                                                                                 String targetCurrency);

}
