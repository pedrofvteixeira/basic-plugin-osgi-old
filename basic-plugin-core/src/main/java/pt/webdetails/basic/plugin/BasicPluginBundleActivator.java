package pt.webdetails.basic.plugin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.pentaho.platform.api.engine.IPentahoObjectReference;
import org.pentaho.platform.api.engine.IPentahoObjectRegistration;
import org.pentaho.platform.engine.core.system.PentahoSystem;
import org.pentaho.platform.engine.core.system.objfac.references.SingletonPentahoObjectReference;
import org.pentaho.platform.plugin.services.pluginmgr.PluginClassLoader;

import java.io.File;
import java.util.Collections;

public class BasicPluginBundleActivator implements BundleActivator {

  protected static Log logger = LogFactory.getLog( BasicPluginBundleActivator.class );

  public static final String PLUGIN_ID = "plugin-id";

  IPentahoObjectRegistration objRegistration;

  @Override
  public void start( BundleContext bundleContext ) throws Exception {

    logger.info( "basic-plugin BundleActivator.start() was triggered" );

    if( PentahoSystem.getInitializedOK() ) {

      PluginClassLoader pluginClassLoader = new PluginClassLoader( new File(""), this.getClass().getClassLoader() );

      // build a PentahoObjectReference to it with attribute map that relates it to 'basic-plugin' pluginId
      IPentahoObjectReference<ClassLoader> objRef =
          new SingletonPentahoObjectReference.Builder<ClassLoader>( ClassLoader.class )
          .object( pluginClassLoader )
          .attributes( Collections.<String, Object>singletonMap( PLUGIN_ID, Constants.PLUGIN_ID ) ).build();


      // Register the classloader with PentahoSystem
      objRegistration = PentahoSystem.registerReference( objRef, ClassLoader.class );
    }
  }

  @Override
  public void stop( BundleContext bundleContext ) throws Exception {

    logger.info( "basic-plugin BundleActivator.stop() was triggered" );

    if( objRegistration != null ) {
      objRegistration.remove();
    }

  }
}
