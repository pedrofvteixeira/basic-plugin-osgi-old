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
