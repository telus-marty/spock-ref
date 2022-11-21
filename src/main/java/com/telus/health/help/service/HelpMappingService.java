package com.telus.health.help.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.google.common.net.PercentEscaper;

@Component
public class HelpMappingService
{
  Logger logger = LoggerFactory.getLogger( HelpMappingService.class );

  private static final PercentEscaper urlEscaper = new PercentEscaper( "-._~!$'()*,;&=@:+/?#", false );
  private final ReadWriteLock lock = new ReentrantReadWriteLock();
  private Date lastModified;
  private Date lastLoadTime;
  private Map<String, String> helpMappings;
  private Set<String> apps;
  private Set<String> languages;

  public String getHelpMapping( String app, String language, String topics )
  {
    checkAndLoadMappings();

    String mapping = validateRequest( app, language );

    if ( StringUtils.isNotEmpty( mapping ) )
    {
      return mapping;
    }

    String key = getMappingKey( app, language, topics );

    mapping = getMapping( key );

    while ( StringUtils.isEmpty( mapping ) )
    {
      key = removeLastTopic( key );

      if ( StringUtils.isEmpty( key ) )
      {
        // No more key to try and map hence not found
        logger.error( "Unable to find a mapping for {} - {} - {}", app, language, topics );
        throw new ServiceException( ServiceMessage.notFound );
      }

      mapping = getMapping( key );
    }

    mapping = fixupAndCleanupTheUrl( mapping );

    return mapping;
  }

  private String validateRequest( String app, String language )
  {
    String mapping = null;

    boolean appExists = apps.contains( app.toLowerCase() );
    boolean languageExists = languages.contains( language.toLowerCase() );

    if ( !appExists )
    {
      logger.warn( "Requested application ({}) not found.", app );
      mapping = getMapping( ServiceMessage.appNotFound.getUrlKey() );
    }
    else if ( !languageExists )
    {
      logger.warn( "Requested langauge ({}) not found.", language );
      mapping = getMapping( ServiceMessage.langNotFound.getUrlKey() );
    }

    if ( ( !appExists || !languageExists ) && StringUtils.isEmpty( mapping ) )
    {
      logger.error( "App and/or language not found ({}.{}) and no error mappings found", app, language );
      throw new ServiceException( ServiceMessage.notFound );
    }

    if ( StringUtils.isNotEmpty( mapping ) )
    {
      mapping = fixupAndCleanupTheUrl( mapping );
    }

    return mapping;
  }

  private String fixupAndCleanupTheUrl( String mapping )
  {
    // trim double quotes
    mapping = StringUtils.strip( mapping, "\"" );

    // handles both "http" and "https"
    if ( !mapping.toLowerCase().startsWith( "http" ) )
    {
      // They're using relative paths so start with our base uri
      String serverPath = getMapping( ServiceMessage.baseUri.getUrlKey() );

      // Then try our config server path
      if ( StringUtils.isEmpty( serverPath ) )
      {
        serverPath = "somurlwhocares";
      }

      if ( serverPath.endsWith( "/" ) )
      {
        serverPath = serverPath.substring( 0, serverPath.lastIndexOf( "/" ) );
      }

      if ( mapping.startsWith( "/" ) )
      {
        mapping = serverPath + mapping;
      }
      else
      {
        mapping = serverPath + "/" + mapping;
      }

    }

    mapping = urlEscaper.escape( mapping );

    return mapping;
  }

  private String getMappingKey( String app, String language, String topics )
  {
    String key = app + "." + language;

    if ( StringUtils.isNotEmpty( topics ) )
    {
      key += "." + topics.replace( "/", "." );
    }

    key = key.toLowerCase();

    return key;
  }

  private String getMapping( String key )
  {
    lock.readLock().lock();

    try
    {
      return helpMappings.get( key );
    }
    finally
    {
      lock.readLock().unlock();
    }
  }

  private void checkAndLoadMappings()
  {

    lock.writeLock().lock();

    try
    {
      logger.debug( "lastModified  = {}", lastModified );
      logger.debug( "lastLoadTime  = {}", lastLoadTime );
      logger.debug( "mappings size = {}", Long.valueOf( helpMappings.size() ) );

      if ( lastLoadTime != null && helpMappings.size() > 0 )
      {
        if ( lastModified != null && lastModified.before( lastLoadTime ) )
        {
          return;
        }
      }

      logger.info( "loading help mappings" );

      try ( InputStream in = getInputStream() )
      {
        Properties properties = new Properties();
        properties.load( in );

        if ( properties.size() > 0 )
        {
          helpMappings.clear();
          apps.clear();
          languages.clear();

          Set<Object> keys = properties.keySet();

          for ( Object key : keys )
          {
            String stringKey = key.toString().toLowerCase();
            helpMappings.put( stringKey, properties.getProperty( stringKey ) );

            if ( isValidApplicationLanguageKey( stringKey ) )
            {
              String bits[] = stringKey.split( "\\." );

              if ( bits.length < 2 )
              {
                logger.warn( "Invalid mapping key found - {}, should have at least app and language (app.lang)", stringKey );
              }
              else
              {
                apps.add( bits[ 0 ] );
                languages.add( bits[ 1 ] );
              }
            }
          }
        }

        lastLoadTime = new Date();
        logger.info( "{} help mappings were loaded", Long.valueOf( helpMappings.size() ) );
      }
    }
    catch ( Exception e )
    {
      logger.error( "checkAndLoadMappings", e );
    }
    finally
    {
      lock.writeLock().unlock();
    }
  }

  private boolean isValidApplicationLanguageKey( String stringKey )
  {
    return !stringKey.equalsIgnoreCase( ServiceMessage.appNotFound.getUrlKey() ) &&
      !stringKey.equalsIgnoreCase( ServiceMessage.langNotFound.getUrlKey() ) &&
      !stringKey.equalsIgnoreCase( ServiceMessage.baseUri.getUrlKey() );
  }

  private InputStream getInputStream() throws IOException
  {
    URL resource = getClass().getClassLoader().getResource( "helpmapping.properties" );
    InputStream inputStream = resource.openConnection().getInputStream();
    return inputStream;
  }

  private String removeLastTopic( String key )
  {
    int index = key.lastIndexOf( "." );

    if ( index == -1 )
    {
      // There are no more periods, we remove the last element all together
      return null;
    }

    return key.substring( 0, index );
  }

  @PostConstruct
  public void init()
  {
    helpMappings = new HashMap<>();
    apps = new TreeSet<>();
    languages = new TreeSet<>();
    checkAndLoadMappings();
  }

}
