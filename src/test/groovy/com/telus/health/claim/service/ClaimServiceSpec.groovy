package com.telus.health.claim.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import com.telus.health.help.SpockRefApplication
import spock.lang.Specification

@SpringBootTest(classes = SpockRefApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource("/application-test.properties")
@ActiveProfiles("test")
class ClaimServiceSpec extends Specification {
  @Autowired ClaimService claimService

  def "test claim accumulation for a recipient"()
  {
    given: "A particular recipient ID"
    def recipientId = 1
    def expectedValue = Double.valueOf(120.00)

    when: "We invoke the accumulations method in our service"
    def sum = claimService.getRecipientBenefitAccumulation(recipientId)

    then: "We have the expected value"
    sum == expectedValue
  }
}
