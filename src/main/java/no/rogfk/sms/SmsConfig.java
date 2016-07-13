package no.rogfk.sms;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsConfig {

    @Bean
    public SmsService smsService() {
        return new SmsService();
    }
}
