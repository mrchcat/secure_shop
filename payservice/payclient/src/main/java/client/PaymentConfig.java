package client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfig {

    @Bean
    PaymentClient getPaymentClient(){
        System.out.println("getPaymentClient");
        return new PaymentClientImpl();
    }
}
