package no.rogfk.sms;

import no.rogfk.sms.exceptions.MissingConfigException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class SmsService {

    @Autowired(required = false)
    private RestTemplate restTemplate;

    @Value("${sms.rest.endpoint}")
    private String endpoint;

    @Value("${sms.rest.mobileparameter}")
    private String mobileParameter;

    @Value("${sms.rest.messageparameter}")
    private String messageParameter;

    @Value("${sms.rest.userparameter}")
    private Optional<String> userParameter;

    @Value("${sms.rest.user}")
    private Optional<String> user;

    @Value("${sms.rest.passwordparameter}")
    private Optional<String> passwordParameter;

    @Value("${sms.rest.password}")
    private Optional<String> password;

    private String message;

    private UriComponentsBuilder builder;

    @PostConstruct
    public void init() {
        verifyConfig();
        builder = UriComponentsBuilder.fromHttpUrl(endpoint);
        userParameter.ifPresent(p -> builder.queryParam(p, user.get()));
        passwordParameter.ifPresent(p -> builder.queryParam(p, password.get()));

        if(restTemplate == null) {
            restTemplate = new RestTemplate();
        }
    }

    private void verifyConfig() {
        if (userParameter.isPresent() && (!user.isPresent())) throw new MissingConfigException("sms.rest.user");
        if (passwordParameter.isPresent() && (!password.isPresent())) throw new MissingConfigException("sms.rest.password");
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String sendSms(String message, String mobile) {
        return restTemplate.getForObject(getUrl(message, mobile), String.class);
    }

    public <T> T sendSms(String message, String mobile, Class<T> clazz) {
        return restTemplate.getForObject(getUrl(message, mobile), clazz);
    }

    String getUrl(String message, String mobile) {
        UriComponentsBuilder urlBuilder = builder.cloneBuilder();
        urlBuilder.queryParam(mobileParameter, mobile);
        urlBuilder.queryParam(messageParameter, message);
        return urlBuilder.build(false).toUriString();
    }
}
