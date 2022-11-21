package com.telus.health.help.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import com.telus.health.help.SpockRefApplication
import spock.lang.Specification

@SpringBootTest(classes = SpockRefApplication.class, webEnvironment =  SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@EnableAutoConfiguration( exclude = [DataSourceAutoConfiguration.class] )
class EchoRandoRestControllerSpec extends Specification {

  @Autowired
  private MockMvc mvc

  def "test GET version"() {
    when: "We issue an HTTP GET to the /version URL"
    def result = mvc.perform(MockMvcRequestBuilders.get("/version")).andReturn()

    then: "HTTP response is OK"
    result.response.status == HttpStatus.OK.value()
    result.response.contentAsString == "1.0"
  }
}
