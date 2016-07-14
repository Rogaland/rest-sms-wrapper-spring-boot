package no.rogfk.sms.annotations;

import no.rogfk.sms.strategy.ManualQueueStrategy;
import no.rogfk.sms.strategy.SendImmediatelyService;
import no.rogfk.sms.strategy.SmsStrategy;

public enum SmsMode {
    SEND_IMMEDIATELY(SendImmediatelyService.class),
    MANUAL_QUEUE(ManualQueueStrategy.class);

    private Class clazz;

    SmsMode(Class clazz) {
        this.clazz = clazz;
    }

    public SmsStrategy instance() {
        try {
            return (SmsStrategy) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Unable to create a new instance of " + clazz.getName());
        }
    }
}
