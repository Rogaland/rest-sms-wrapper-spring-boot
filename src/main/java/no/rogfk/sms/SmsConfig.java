package no.rogfk.sms;

import no.rogfk.sms.annotations.EnableSmsWrapper;
import no.rogfk.sms.annotations.SmsMode;
import no.rogfk.sms.strategy.SmsStrategy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;

import java.util.Map;

@Configuration
public class SmsConfig implements ApplicationContextAware {

    @Value("${sms.mode:}")
    private String configMode;

    private SmsMode smsMode;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (StringUtils.isEmpty(configMode)) {
            Map<String, Object> beans = applicationContext.getBeansWithAnnotation(EnableSmsWrapper.class);
            if (beans.size() == 1) {
                Object bean = beans.values().iterator().next();
                EnableSmsWrapper annotation = AnnotationUtils.findAnnotation(bean.getClass(), EnableSmsWrapper.class);
                smsMode = annotation.mode();
            } else {
                throw new IllegalStateException("Expected to find 1 class annotated with @EnableSmsWrapper, but found " + beans.size());
            }
        } else {
            smsMode = SmsMode.valueOf(configMode);
        }
    }

    @Bean
    public SmsStrategy smsStrategy() {
        return smsMode.instance();
    }

    @Bean
    public SmsService smsService() {
        return new SmsService();
    }

}
