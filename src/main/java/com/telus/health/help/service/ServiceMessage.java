package com.telus.health.help.service;

public enum ServiceMessage
{
  ok( 0 ),
  internalError( 1 ),
  langNotFound( 2, "error.language.notfound" ),
  appNotFound( 3, "error.app.notfound" ),
  notFound( 4 ),
  baseUri( 5, "base.uri" );

  private long code;
  private String urlKey;

  ServiceMessage( long code )
  {
    this( code, null );
  }

  ServiceMessage( long code, String urlKey )
  {
    this.code = code;
    this.urlKey = urlKey;
  }

  public long getCode()
  {
    return code;
  }

  public String getUrlKey()
  {
    return urlKey;
  }
}
