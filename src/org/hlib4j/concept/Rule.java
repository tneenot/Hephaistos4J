/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.concept;

import org.hlib4j.collection.Collections;

/**
 * A rule is a concept that's controlling the validity for an element. Rule
 * allows to attach a control on the element itself. The {@link #accept(Object)}
 * method's allows to control if an element is valid with the rule
 * implementation and can be accepted. <br><br>
 * <p>
 * A rule can be linked to a collection for managing this collection during data
 * insertion for example.
 *
 * @param <E> Element managed by this rule.
 * @author Tioben Neenot
 * @see Collections
 */
public interface Rule<E>
{

  /**
   * Verifies if the element is valid according to the rule.
   *
   * @param element Element to control by the rule.
   * @return <code>true</code> if the rule implementation determines if the
   * element is valid, <code>false</code> otherwise.
   */
  boolean accept(E element);
}
