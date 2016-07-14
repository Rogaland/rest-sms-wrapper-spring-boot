package no.rogfk.sms.strategy;

public class SendImmediatelyService extends AbstractSmsStrategy {

    public <T> T sendSms(String url, Class<T> clazz) {
        return restTemplate.getForObject(url, clazz);
    }

}
