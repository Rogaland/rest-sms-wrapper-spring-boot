package no.rogfk.sms.strategy

import spock.lang.Specification

class ManualQueueStrategySpec extends Specification {

    private ManualQueueStrategy manualQueueStrategy

    void setup() {
        manualQueueStrategy = new ManualQueueStrategy()
    }

    def "Send SMS, String response"() {
        when:
        def response = manualQueueStrategy.sendSms("http://localhost", String)

        then:
        response == "Added to SMS queue"
        manualQueueStrategy.getQueue().get(0) == "http://localhost"
    }

    def "Clear SMS queue"() {
        when:
        manualQueueStrategy.sendSms("http://localhost", String)
        manualQueueStrategy.emptyQueue()

        then:
        manualQueueStrategy.getQueue().size() == 0
    }

    def "Get formatted values, without query params"() {
        when:
        manualQueueStrategy.sendSms("http://localhost", String)
        def values = manualQueueStrategy.getFormattedValues()

        then:
        values.size() == 1
        values.get(0).host == "http://localhost"
        values.get(0).values.size() == 0
    }

    def "Get formatted values, with query params"() {
        when:
        manualQueueStrategy.sendSms("http://localhost?test1=value1&test2=value2&test3=value3", String)
        def values = manualQueueStrategy.getFormattedValues()

        then:
        values.size() == 1
        values.get(0).host == "http://localhost"
        values.get(0).values.size() == 3
    }
}
