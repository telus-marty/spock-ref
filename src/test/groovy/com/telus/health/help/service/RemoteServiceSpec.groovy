package com.telus.health.help.service

import org.springframework.boot.test.context.SpringBootTest
import com.telus.health.claim.service.RemoteService
import com.telus.health.help.SpockRefApplication
import spock.lang.Specification

@SpringBootTest(classes = SpockRefApplication.class, webEnvironment =  SpringBootTest.WebEnvironment.MOCK)
class RemoteServiceSpec extends Specification {

  // Mock means we simply get an "empty" class
  def service = Mock(RemoteService)

  def newTest()
  {
    given:
    when:
    service.executeRemoteAction( "data" )
    then: "We get no errors"
    noExceptionThrown()
  }
}
