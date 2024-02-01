package az.unitech.mscurrencyrate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
public class MsCurrencyRateApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsCurrencyRateApplication.class, args);
    }

}
