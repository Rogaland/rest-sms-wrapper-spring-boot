package no.rogfk.sms.annotations;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import no.rogfk.sms.SmsService;
import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ComponentScan(basePackageClasses = SmsService.class)
@EnableEncryptableProperties
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnableSmsWrapper {
}
