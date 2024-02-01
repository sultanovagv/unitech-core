package az.unitech.mscurrencyrate.controller;

import az.unitech.mscurrencyrate.entity.CurrencyRateEntity;
import az.unitech.mscurrencyrate.service.CurrencyRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CurrencyRateController {

    private final CurrencyRateService currencyRateService;

    @GetMapping("/currency-rate")
    public ResponseEntity<CurrencyRateEntity> getCurrencyRate(@RequestParam String sourceCurrency,
                                                              @RequestParam String targetCurrency) {
        return ResponseEntity.ok(currencyRateService.getCurrencyRate(sourceCurrency, targetCurrency));
    }


}
