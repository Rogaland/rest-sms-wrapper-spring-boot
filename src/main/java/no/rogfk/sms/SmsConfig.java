package no.rogfk.sms;

import no.rogfk.sms.annotations.EnableSmsWrapper;
import no.rogfk.sms.annotations.SmsMode;
import no.rogfk.sms.strategy.SendImmediatelyService;
import no.rogfk.sms.strategy.SmsStrategy;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Map;

@Configuration
public class SmsConfig implements ApplicationContextAware {

    private SmsMode smsMode;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(EnableSmsWrapper.class);
        if (beans.size() == 1) {
            Object bean = beans.values().iterator().next();
            EnableSmsWrapper annotation = AnnotationUtils.findAnnotation(bean.getClass(), EnableSmsWrapper.class);
            smsMode = annotation.mode();
        } else {
            throw new IllegalStateException("Expected to find 1 class annotated with @EnableSmsWrapper, but found " + beans.size());
        }
    }

    @Bean
    public SmsStrategy smsStrategy() {
        switch (smsMode) {
            case SEND_IMMEDIATELY:
                return new SendImmediatelyService();
            default:
                throw new IllegalArgumentException("Unknown mode: " + smsMode.name());
        }
    }

    @Bean
    public SmsService smsService() {
        return new SmsService();
    }

}
