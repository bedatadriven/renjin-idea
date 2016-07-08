
/*
 * Copyright 2011-2011 Gregory Shrago
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.renjin.idea.psi;

import com.intellij.psi.tree.IElementType;
import org.renjin.idea.lang.RLanguage;


/**
 * @author Holger Brandl
 */
public class RCompositeElementType extends IElementType {

  public RCompositeElementType(String debug) {
    super(debug, RLanguage.INSTANCE);
  }
}