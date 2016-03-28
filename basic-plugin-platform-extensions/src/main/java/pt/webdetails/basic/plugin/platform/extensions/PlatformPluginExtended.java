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
