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
 * Multiple is a <code>Rule</code>, computes the multiple for the number value.
 *
 * @param <E> Type of number
 * @author Tioben Neenot
 */
public class Multiple<E extends Number> implements Rule<E>
{
// >>> Uses the E into constructor rather than to implement each number type. Fix accept and construction with synchronized to take
// account the hypothesis where AtomicInteger would be able to be used to.
  /**
   * Element reference for multiple
   */
  private final Number refValue;

  /**
   * Builds an instance of the Multiple for a number of E type
   *
   * @param aNumber Reference value of the multiple
   */
  public Multiple(E aNumber)
  {
    synchronized (this)
    {
      this.refValue = aNumber;
    }
  }

  public boolean accept(E element)
  {
    synchronized (this)
    {
      try
      {
        return (element.doubleValue() % this.refValue.doubleValue()) == 0.0;
      } catch (ClassCastException e)
      {
        // Do nothing, only for implementation
      }
    }

    return false;
  }
}
