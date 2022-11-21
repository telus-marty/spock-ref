package com.telus.health.help.service


import spock.lang.Specification
import spock.lang.Unroll

class HelpMappingServiceSpec extends Specification {

  private HelpMappingService helpMappingService = new HelpMappingService()

  @Unroll
  def "Test helpmapping - #scenario"() {
    given: "A configuration file (see helpmapping.properties), and a set of requests"
    helpMappingService.init(  )

    when: "We make a call to the mapping services"
    def mapping = null
    def serviceMessage = null

    try {
      mapping = helpMappingService.getHelpMapping(application, language, topics)
    } catch ( ServiceException se) {
      serviceMessage = se.message
    }

    then: "We get a valid mapping or a service message from an exception"
    mapping == expectedMapping
    serviceMessage == expectedServiceMessage

    where: "The application and topics are as follows"
    application | language | topics   | expectedMapping                                   | expectedServiceMessage | scenario
    // Various basic mappings to validate case insensitivity
    "decm"      | "en"     | null     | "http://localhost:2015/decm_fr.html"              | null                   | "Test english default mapping"
    "decm"      | "fr"     | null     | "http://localhost:2015/decm_fr.html"              | null                   | "Test french default mapping"
  }
}
