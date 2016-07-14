package no.rogfk.sms.strategy.dto;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class FormattedValue {
    private String host;
    private List<String> values;

    public FormattedValue(String host, String... values) {
        this.host = host;
        this.values = Arrays.asList(values);
    }
}
