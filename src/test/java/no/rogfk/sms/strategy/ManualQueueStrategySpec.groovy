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
        manualQueueStrategy.removeAll()

        then:
        manualQueueStrategy.getQueue().size() == 0
    }
}
