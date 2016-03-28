package pt.webdetails.basic.plugin.platform.extensions;

import org.pentaho.platform.api.engine.IPluginOperation;
import org.pentaho.platform.engine.core.solution.ContentInfo;

import java.util.List;

/**
 * This class extends platform-core's ContentInfo to offer some public accessors.
 **/
public class ContentInfoExtended extends ContentInfo {

  /** 
   * Sets the operations list
   *
   * @param operations IPluginOperation list
   **/
  public void setOperations( List<IPluginOperation> operations ) {

    if( operations != null ) {

      getOperations().clear();

      for( IPluginOperation operation : operations ){
        addOperation( operation );
      }
    }
  }
}
