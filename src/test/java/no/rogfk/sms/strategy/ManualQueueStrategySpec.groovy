package no.rogfk.sms.strategy

import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import java.util.function.Predicate

class ManualQueueStrategySpec extends Specification {

    private ManualQueueStrategy manualQueueStrategy
    private RestTemplate restTemplate

    void setup() {
        restTemplate = Mock(RestTemplate)
        manualQueueStrategy = new ManualQueueStrategy(restTemplate: restTemplate)
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

    def "Send SMS and clear list"() {
        given:
        manualQueueStrategy.add("http://localhost1")
        manualQueueStrategy.add("http://localhost2")
        Predicate<String> validResponse = { response -> response.equals("Test response") } as Predicate<String>

        when:
        manualQueueStrategy.sendQueue(validResponse)

        then:
        2 * restTemplate.getForObject(_ as String, _ as Class) >> "Test response"
        manualQueueStrategy.getQueue().size() == 0
    }

    def "Send SMS, do not clear list if response is not valid"() {
        given:
        manualQueueStrategy.add("http://localhost1")
        manualQueueStrategy.add("http://localhost2")
        Predicate<String> validResponse = { response -> false } as Predicate<String>

        when:
        manualQueueStrategy.sendQueue(validResponse)

        then:
        2 * restTemplate.getForObject(_ as String, _ as Class) >> "Test response"
        manualQueueStrategy.getQueue().size() == 2
    }
}
