package com.telus.health.help.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoRandoRestController implements EchoRandoController
{
  @Override
  public String getVersion()
  {
    return "1.0";
  }

  @Override
  public String echo( String value )
  {
    return value;
  }

  @Override
  public ResponseEntity<Integer> randomValue( Integer min, Integer max )
  {
    int random = (int) ( Math.random() * max.intValue() + min.intValue() );
    return ResponseEntity.ok( Integer.valueOf( random ) );
  }
}
