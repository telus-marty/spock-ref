package com.telus.health.help.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Validated
public interface EchoRandoController
{
  @RequestMapping( value = "/version", produces = MediaType.TEXT_PLAIN_VALUE, method = RequestMethod.GET )
  String getVersion();

  @RequestMapping( value = "/echo", produces = MediaType.TEXT_PLAIN_VALUE, method = RequestMethod.GET )
  String echo( @RequestParam( "value" ) String value );

  @RequestMapping( value = "/rando", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET )
  ResponseEntity<Integer> randomValue( @RequestParam( "min" ) Integer min, @RequestParam( "max" ) Integer max );
}
