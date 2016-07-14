package no.rogfk.sms;

import no.rogfk.sms.exceptions.MissingConfigException;
import no.rogfk.sms.strategy.SmsStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.util.Optional;

public class SmsService {

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

    @Autowired
    private SmsStrategy smsStrategy;

    private String message;

    private UriComponentsBuilder builder;

    @PostConstruct
    public void init() {
        verifyConfig();
        builder = UriComponentsBuilder.fromHttpUrl(endpoint);
        userParameter.ifPresent(p -> builder.queryParam(p, user.get()));
        passwordParameter.ifPresent(p -> builder.queryParam(p, password.get()));
    }

    private void verifyConfig() {
        if (userParameter.isPresent() && (!user.isPresent())) throw new MissingConfigException("sms.rest.user");
        if (passwordParameter.isPresent() && (!password.isPresent()))
            throw new MissingConfigException("sms.rest.password");
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String sendSms(String message, String mobile) {
        return smsStrategy.sendSms(getUrl(message, mobile), String.class);
    }

    public <T> T sendSms(String message, String mobile, Class<T> clazz) {
        return smsStrategy.sendSms(getUrl(message, mobile), clazz);
    }

    String getUrl(String message, String mobile) {
        UriComponentsBuilder urlBuilder = builder.cloneBuilder();
        urlBuilder.queryParam(mobileParameter, mobile);
        urlBuilder.queryParam(messageParameter, message);
        return urlBuilder.build(false).toUriString();
    }
}
