package no.rogfk.sms.strategy;

public interface SmsStrategy {

    <T> T sendSms(String url, Class<T> clazz);

}
