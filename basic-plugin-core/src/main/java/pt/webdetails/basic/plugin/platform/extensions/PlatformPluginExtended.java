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
package pt.webdetails.basic.plugin.platform.extensions;

import org.pentaho.platform.plugin.services.pluginmgr.PlatformPlugin;

import java.util.List;
import java.util.Map;

/**
  * This class extends platform-extension's PlatformPlugin to offer some public accessors.
 **/
public class PlatformPluginExtended extends PlatformPlugin {

  /**
   * Sets the static resources map
   *
   * @param resourceMap resource map
   */
  public void setStaticResourceMap( Map<String, String> resourceMap ) {

    if( resourceMap != null ) {

      getStaticResourceMap().clear();

      for( String key : resourceMap.keySet() ){
        addStaticResourcePath( key, resourceMap.get( key ) );
      }
    }
  }

  /**
   * Sets the external resources map
   *
   * @param externalResources external resources map
   */
  public void setExternalResourcesMap( Map<String, List<String>> externalResources ) {

    if( externalResources != null ) {

      for( String key : externalResources.keySet() ){

        List<String> extResourcesByKey = externalResources.get( key );

        for ( String externalResource : extResourcesByKey ) {
          addExternalResource( key , externalResource );
        }
      }
    }
  }

}
