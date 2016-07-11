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

    @Autowired
    private RestTemplate restTemplate;

    @Value("${sms.rest.endpoint}")
    private Optional<String> endpoint;

    @Value("${sms.rest.userparameter}")
    private Optional<String> userParameter;

    @Value("${sms.rest.user}")
    private Optional<String> user;

    @Value("${sms.rest.passwordparameter}")
    private Optional<String> passwordParameter;

    @Value("${sms.rest.password}")
    private Optional<String> password;

    @Value("${sms.rest.mobileparameter}")
    private Optional<String> mobileParameter;

    @Value("${sms.rest.messageparameter}")
    private Optional<String> messageParameter;

    private String message;


    @Value("sms.rest.successvalue")
    Optional<String> successValue;

    @PostConstruct
    public void checkConfig() {
        if (!endpoint.isPresent()) throw new MissingConfigException("sms.rest.endpoint");
        if (!mobileParameter.isPresent()) throw new MissingConfigException("sms.rest.mbileparameter");
        if (!messageParameter.isPresent()) throw new MissingConfigException("sms.rest.messageparameter");
        if (!successValue.isPresent()) throw new MissingConfigException("sms.rest.successvalue");

        if (userParameter.isPresent() && (!user.isPresent()) throw new MissingConfigException("sms.rest.user");
        if (passwordParameter.isPresent() && (!password.isPresent())) throw new MissingConfigException("sms.rest.password");
    }

    public SmsService() {

    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object sendSms(String message, String mobile, Object responseType) {
        return restTemplate.getForObject(getUrl(message, mobile), responseType.getClass());
    }

    private String getUrl(String message, String mobile) {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(endpoint.get());
        userParameter.ifPresent(p -> urlBuilder.queryParam(p, user.get()));
        passwordParameter.ifPresent(p -> urlBuilder.queryParam(p, password.get()));
        urlBuilder.queryParam(mobileParameter.get(), mobile);
        urlBuilder.queryParam(messageParameter.get(), message);

        return urlBuilder.build(false).encode().toUriString();

    }
}
