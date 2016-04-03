/*!
* Copyright 2002 - 2016 Webdetails, a Pentaho company.  All rights reserved.
*
* This software was developed by Webdetails and is provided under the terms
* of the Mozilla Public License, Version 2.0, or any later version. You may not use
* this file except in compliance with the license. If you need a copy of the license,
* please go to  http://mozilla.org/MPL/2.0/. The Initial Developer is Webdetails.
*
* Software distributed under the Mozilla Public License is distributed on an "AS IS"
* basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to
* the license for the specific language governing your rights and limitations.
*/
package pt.webdetails.basic.plugin;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.pentaho.platform.api.repository2.unified.IUnifiedRepository;
import org.pentaho.platform.engine.core.system.PentahoSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.webdetails.basic.plugin.api.IBasicPluginSettings;

@Path( "/api" )
public class BasicPluginCxfRestAPI {

  private Logger logger = LoggerFactory.getLogger( getClass() );

  private IBasicPluginSettings settings;

  private IUnifiedRepository repository;

  public BasicPluginCxfRestAPI( IBasicPluginSettings settings, IUnifiedRepository repository ) {
    setSettings( settings );
    setRepository( safetyCheckUnifiedRepository( repository ) );
  }

  @GET
  @Path( "/hello" )
  @Produces( MediaType.TEXT_PLAIN )
  public String hello() {
    logger.info( "CXF REST API called on endpoint /hello" );
    return getSettings().getSaySomething();
  }

  public IUnifiedRepository getRepository() {
    return repository;
  }

  public void setRepository( IUnifiedRepository repository ) {
    this.repository = repository;
  }

  public IBasicPluginSettings getSettings() {
    return settings;
  }

  public void setSettings( IBasicPluginSettings settings ) {
    this.settings = settings;
  }

  private IUnifiedRepository safetyCheckUnifiedRepository( IUnifiedRepository repository ) {

    try {

      if( repository != null && repository.toString() != null ){
        return repository;
      }

    } catch ( Throwable t ) {
      /* do nothing */
    }

    // fallback
    return PentahoSystem.get( IUnifiedRepository.class );
  }
}
