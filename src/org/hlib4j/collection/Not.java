/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.collection;

import org.hlib4j.concept.Rule;
import org.hlib4j.util.States;

/**
 * Builds a predicate whose the result is the opposite to the parent predicate itself. The predicate is given by a
 * method for an object, that will return a value.
 *
 * @author Tioben Neenot
 */
public class Not<E> implements Rule<E>
{

  private final E refValue;

  /**
   * Builds an instance of the opposite of a {@link PredicateMethod} according to model and method's name.
   *
   * @param refValue Reference value for this rule.
   */
  public Not(E refValue)
  {
    this.refValue = refValue;
  }


  @Override
  public boolean accept(E element)
  {
    if (areNullParameterAndRefValue(element)) return false;

    // Otherwise, compare the parameter with the reference value
    return !this.refValue.equals(element);
  }

  private boolean areNullParameterAndRefValue(E theValue)
  {
    // If the reference value and the comparison value are null so return the opposite of this state
    return States.isNullOrEmpty(this.refValue) & States.isNullOrEmpty(theValue);
  }

}
