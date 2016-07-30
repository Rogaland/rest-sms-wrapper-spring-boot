package no.rogfk.sms

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationConfiguration
import spock.lang.Specification

@IntegrationTest
@SpringApplicationConfiguration(classes = TestApplication)
class SmsServiceSpec extends Specification {

    @Autowired
    private SmsService smsService

    def "Verify startup config"() {
        when:
        smsService.init()

        then:
        noExceptionThrown()
    }

    def "Get url"() {
        when:
        def url = smsService.getUrl("test message", "12345678")

        then:
        url.contains("sUser")
        url.contains("sPassword")
        url.contains("sMsg")
        url.contains("sMobile")
    }
}
