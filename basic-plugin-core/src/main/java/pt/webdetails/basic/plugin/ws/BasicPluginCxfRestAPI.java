package pt.webdetails.basic.plugin.ws;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.platform.api.repository2.unified.IUnifiedRepository;
import org.pentaho.platform.engine.core.system.PentahoSystem;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path( "/api" )
public class BasicPluginCxfRestAPI {

  protected static Log logger = LogFactory.getLog( BasicPluginCxfRestAPI.class );

  private IUnifiedRepository repository;

  public BasicPluginCxfRestAPI( IUnifiedRepository repository ) {

    try {

      if( repository != null && repository.toString() != null ){
        setRepository( repository );
        return;
      }

    } catch ( Throwable t ) {
      /* do nothing */
    }

    setRepository( PentahoSystem.get( IUnifiedRepository.class ) ); /* fallback */
  }

  @GET
  @Path( "/ready" )
  @Produces( MediaType.TEXT_PLAIN )
  public String ready() {
    logger.info( "basic-plugin CXF REST API called for endpoint: /ready" );
    return Boolean.toString( true );
  }

  public IUnifiedRepository getRepository() {
    return repository;
  }

  public void setRepository( IUnifiedRepository repository ) {
    this.repository = repository;
  }
}
