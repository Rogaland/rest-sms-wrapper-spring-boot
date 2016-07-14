package no.rogfk.sms.strategy;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ManualQueueStrategy extends AbstractSmsStrategy {

    private List<String> smsQueue = new ArrayList<>();

    @Override
    @SuppressWarnings("unchecked")
    public <T> T sendSms(String url, Class<T> clazz) {
        smsQueue.add(url);
        if (clazz == String.class) {
            return (T) "Added to SMS queue";
        } else {
            try {
                return clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new IllegalStateException("Unable to create a new instance of " + clazz.getName());
            }
        }
    }

    public List<String> getQueue() {
        return smsQueue;
    }

    public void removeAll() {
        smsQueue.clear();
    }

    public void add(String url) {
        smsQueue.add(url);
    }

    public void send() {
        smsQueue.forEach(url -> {
            String response = super.sendSms(url, String.class);
            log.info("Response: {}", response);
        });
    }
}
