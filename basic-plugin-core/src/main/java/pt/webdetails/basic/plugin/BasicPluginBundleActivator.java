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

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.pentaho.platform.api.engine.IPentahoObjectReference;
import org.pentaho.platform.api.engine.IPentahoObjectRegistration;
import org.pentaho.platform.engine.core.system.PentahoSystem;
import org.pentaho.platform.engine.core.system.objfac.references.SingletonPentahoObjectReference;
import org.pentaho.platform.plugin.services.pluginmgr.PluginClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.webdetails.basic.plugin.api.IBasicPluginSettings;

import java.io.File;
import java.util.Collections;

public class BasicPluginBundleActivator implements BundleActivator {

  private Logger logger = LoggerFactory.getLogger( getClass() );

  public static final String PLUGIN_ID_KEY = "plugin-id";

  IPentahoObjectRegistration objRegistration;

  @Override
  public void start( BundleContext bundleContext ) throws Exception {

    logger.info( "BundleActivator.start() triggered" );

    if( PentahoSystem.getInitializedOK() ) {

      PluginClassLoader pluginClassLoader = new PluginClassLoader( new File(""), this.getClass().getClassLoader() );

      // build a PentahoObjectReference to it with attribute map that relates it to 'basic-plugin' pluginId
      IPentahoObjectReference<ClassLoader> objRef =
          new SingletonPentahoObjectReference.Builder<ClassLoader>( ClassLoader.class )
          .object( pluginClassLoader )
          .attributes( Collections.<String, Object>singletonMap( PLUGIN_ID_KEY, IBasicPluginSettings.PLUGIN_ID ) ).build();


      // Register the classloader with PentahoSystem
      objRegistration = PentahoSystem.registerReference( objRef, ClassLoader.class );
    }
  }

  @Override
  public void stop( BundleContext bundleContext ) throws Exception {

    logger.info( "BundleActivator.stop() triggered" );

    if( objRegistration != null ) {
      objRegistration.remove();
    }

  }
}
