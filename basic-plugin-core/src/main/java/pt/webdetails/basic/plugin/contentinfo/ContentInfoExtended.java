package pt.webdetails.basic.plugin.contentinfo;

import org.pentaho.platform.api.engine.IPluginOperation;
import org.pentaho.platform.engine.core.solution.ContentInfo;

import java.util.List;

public class ContentInfoExtended extends ContentInfo {

  public void setOperations( List<IPluginOperation> operations ) {

    if( operations != null ) {

      getOperations().clear();

      for( IPluginOperation operation : operations ){
        addOperation( operation );
      }
    }
  }
}
