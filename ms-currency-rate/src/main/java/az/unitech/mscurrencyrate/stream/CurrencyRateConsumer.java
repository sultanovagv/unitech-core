package az.unitech.mscurrencyrate.stream;

import az.unitech.mscurrencyrate.model.CurrencyRateDto;
import az.unitech.mscurrencyrate.service.CurrencyRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Service("currency-rate")
@RequiredArgsConstructor
@Slf4j
public class CurrencyRateConsumer implements Consumer<Message<CurrencyRateDto>> {

    private final CurrencyRateService service;

    @Transactional
    @Override
    public void accept(Message<CurrencyRateDto> message) {
        log.info("Message was received : {}", message.getPayload());
        service.saveCurrencyRate(message.getPayload());


        String fromCurrency = message.getPayload().getSourceCurrency();
        String toCurrency = message.getPayload().getTargetCurrency();
        service.evictCurrencyRateCache(fromCurrency, toCurrency);
    }

}
