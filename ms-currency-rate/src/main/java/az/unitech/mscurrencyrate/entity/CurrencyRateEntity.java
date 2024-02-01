package az.unitech.mscurrencyrate.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "currency_rate")
public class CurrencyRateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal exchangeRate;
    private LocalDateTime updateTime;
    private LocalDateTime eventTime;

}
