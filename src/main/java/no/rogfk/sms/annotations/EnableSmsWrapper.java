package no.rogfk.sms.annotations;

import no.rogfk.sms.SmsConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Import(SmsConfig.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnableSmsWrapper {
    SmsMode mode() default SmsMode.SEND_IMMEDIATELY;
}
