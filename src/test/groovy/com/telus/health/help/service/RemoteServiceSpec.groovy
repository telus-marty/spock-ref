package com.telus.health.help.service

import org.springframework.boot.test.context.SpringBootTest
import com.telus.health.help.SpockRefApplication
import spock.lang.Specification

@SpringBootTest(classes = SpockRefApplication.class, webEnvironment =  SpringBootTest.WebEnvironment.MOCK)
class RemoteServiceSpec extends Specification{
}
