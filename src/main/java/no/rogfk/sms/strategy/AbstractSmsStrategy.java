package no.rogfk.sms.strategy;

import no.rogfk.sms.strategy.dto.FormattedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractSmsStrategy implements SmsStrategy {

    @Autowired(required = false)
    protected RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }
    }

    public <T> T sendSms(String url, Class<T> clazz) {
        return restTemplate.getForObject(url, clazz);
    }

    protected List<FormattedValue> getFormattedValues(List<String> values) {
        return values.stream().map(url -> {
            int queryParamStart = url.indexOf("?");
            if(queryParamStart > 0) {
                String host = url.substring(0, queryParamStart);
                String[] queryParams = url.substring(queryParamStart + 1).split("&");
                return new FormattedValue(host, queryParams);
            } else {
                return new FormattedValue(url);
            }
        }).collect(Collectors.toList());
    }

}
