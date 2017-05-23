/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.collection;

import org.hlib4j.concept.Rule;

/**
 * True rule. A class rule that's returning always
 * <code>true</code> while {@link True#accept(java.lang.Object)} is calling.
 *
 * @author Tioben Neenot.
 */
public class True implements Rule<Object>
{

  @Override
  public boolean accept(Object element)
  {
    return true;
  }
}
