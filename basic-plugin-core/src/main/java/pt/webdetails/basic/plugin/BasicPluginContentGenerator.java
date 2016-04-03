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

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.pentaho.platform.api.engine.IParameterProvider;
import org.pentaho.platform.api.repository2.unified.IUnifiedRepository;
import org.pentaho.platform.api.repository2.unified.RepositoryFile;
import org.pentaho.platform.api.repository2.unified.data.simple.SimpleRepositoryFileData;
import org.pentaho.platform.engine.core.system.PentahoSystem;
import org.pentaho.platform.engine.services.solution.BaseContentGenerator;
import org.pentaho.platform.util.messages.LocaleHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.webdetails.basic.plugin.api.IBasicPluginSettings;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;

public class BasicPluginContentGenerator extends BaseContentGenerator {

  private static final String PATH_PARAMETER_ID = "path";

  private Logger logger = LoggerFactory.getLogger( getClass() );

  private IBasicPluginSettings settings;

  private IUnifiedRepository repository;

  public BasicPluginContentGenerator( IBasicPluginSettings settings, IUnifiedRepository repository ) {
    setSettings( settings );
    setRepository( safetyCheckUnifiedRepository ( repository ) );
  }

  @Override public void createContent() throws Exception {

    IParameterProvider pathParams = parameterProviders.get( PATH_PARAMETER_ID );

    RepositoryFile file;
    String urlEncodedFilePath;

    if( pathParams != null
        && !StringUtils.isEmpty( ( urlEncodedFilePath = pathParams.getStringParameter( PATH_PARAMETER_ID, null ) ) ) ) {

      String filePath = URLDecoder.decode( urlEncodedFilePath, LocaleHelper.UTF_8 );

      logger.info( "ContentGenerator.createContent() called for file: " + filePath );

      getResponse().setContentType( getSettings().getMimeType() );
      getResponse().setHeader( "Cache-Control", "no-cache" );

      if ( ( file = getRepository().getFile( filePath ) ) == null ) {
        throw new WebApplicationException( Response.Status.NOT_FOUND );
      }

      SimpleRepositoryFileData data = getRepository().getDataForRead( file.getId(), SimpleRepositoryFileData.class );

      writeOutAndFlush( getResponse().getOutputStream(), data.getInputStream() );
    }
  }

  @Override public Log getLogger() { return null; }

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

  protected HttpServletResponse getResponse(){
    return ( HttpServletResponse ) parameterProviders.get( PATH_PARAMETER_ID ).getParameter( "httpresponse" );
  }

  private IUnifiedRepository safetyCheckUnifiedRepository( IUnifiedRepository repository ) {

    try {

      if( repository != null && repository.toString() != null ){
        return repository;
      }

    } catch ( Throwable t ) {
      if( getLogger() != null ){
        getLogger().error( t );
      }
    }

    // fallback
    return PentahoSystem.get( IUnifiedRepository.class );
  }

  private void writeOutAndFlush( OutputStream out, InputStream data ) {
    try {

      if ( data != null && out != null ) {
        IOUtils.copy( data, out );
        out.flush();
      }

    } catch ( Throwable t ) {
      if( getLogger() != null ){
        getLogger().error( t );
      }
    }
  }
}
