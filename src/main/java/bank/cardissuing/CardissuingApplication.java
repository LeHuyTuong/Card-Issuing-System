package bank.cardissuing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class CardissuingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardissuingApplication.class, args);
    }

}
