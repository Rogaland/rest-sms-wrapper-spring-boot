package no.rogfk.sms.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

public abstract class AbstractSmsStrategy implements SmsStrategy {

    @Autowired(required = false)
    protected RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }
    }

}
