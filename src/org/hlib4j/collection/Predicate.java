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
 * A convenience class to compare a reference value with another one.
 * The comparison has validated if and
 * only if the &lt;E&gt; type referenced from the constructor, is equal to the
 * the value type given in the {@link #accept(Object)} method. For example,
 * suppose we have a class like this:<br>
 * <pre>
 * class A
 * {
 *   private boolean aFlag;
 *
 *   public A(boolean v) { aFlag = v; }
 *
 *   public boolean isFlag() { return aFlag; }
 * }</pre>
 * <br>
 * To use the {@link Predicate} with this class, implement the following rule:<br>
 * <br>
 * <pre>
 * Predicate&lt;Integer&gt; e = new Predicate&lt;A&gt;(5);
 * ...
 * int another_int = 5;
 * e.accept(another_int);
 * </pre>
 * <br>
 * The method will return <code>true</code> if the other int has the
 * same value than the first given as reference into the constructor.<br>
 * <br>
 *
 * @param <E> value type for comparison
 * @author Tioben Neenot
 */
public class Predicate<E> implements Rule<E>
{

  /**
   * The equal clause to gets the referenced value
   */
  private E simpleValue = null;


  /**
   * Builds an instance of a PredicateMethod with a value only as reference.
   *
   * @param simpleValue Reference value
   */
  public Predicate(E simpleValue)
  {
    this.simpleValue = simpleValue;
  }


  /**
   * @see Rule#accept(Object)
   */
  @Override
  public boolean accept(E element)
  {

    return States.isNullOrEmpty(this.simpleValue) ? this.simpleValue == element : this.simpleValue.equals(element);
  }
}
