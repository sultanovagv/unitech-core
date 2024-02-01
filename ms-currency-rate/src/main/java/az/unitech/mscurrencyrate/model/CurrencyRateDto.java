package az.unitech.mscurrencyrate.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CurrencyRateDto {
    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal exchangeRate;
    private LocalDateTime updateTime;
}
