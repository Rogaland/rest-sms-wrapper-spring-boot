package no.rogfk.sms.exceptions;

public class MissingConfigException extends RuntimeException {
    public MissingConfigException(String config) {
        super(String.format("Configuration value missing: %s", config));
    }
}
