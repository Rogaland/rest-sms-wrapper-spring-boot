package no.rogfk.sms.strategy;

import lombok.extern.slf4j.Slf4j;
import no.rogfk.sms.strategy.dto.FormattedValue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

@Slf4j
public class ManualQueueStrategy extends AbstractSmsStrategy {

    private Set<String> smsQueue = new HashSet<>();

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
        return new ArrayList<>(smsQueue);
    }

    public List<FormattedValue> getFormattedValues() {
        return super.getFormattedValues(getQueue());
    }

    public void emptyQueue() {
        smsQueue.clear();
    }

    public void add(String url) {
        smsQueue.add(url);
    }

    public void sendQueue(Predicate<String> sentResponse) {
        Set<String> queueCopy = new HashSet<>(smsQueue);
        for (String url : queueCopy) {
            String response = super.sendSms(url, String.class);
            log.info("Response: {}", response);
            if (sentResponse.test(response)) {
                log.debug("Response OK, removing from list");
                smsQueue.remove(url);
            } else {
                log.warn("Response not OK, keeping SMS in queue: {} (response: '{}')", url, response);
            }
        }
    }
}
