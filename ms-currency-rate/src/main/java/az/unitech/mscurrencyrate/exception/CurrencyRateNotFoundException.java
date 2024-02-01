package az.unitech.mscurrencyrate.exception;

public class CurrencyRateNotFoundException extends RuntimeException {
    public CurrencyRateNotFoundException() {
    }

    public CurrencyRateNotFoundException(String message) {
        super(message);
    }
}
