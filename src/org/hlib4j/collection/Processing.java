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
 * Runs a process on a specific value given as argument. The Processing class is
 * using within a collection for which a {@link Rule} is applying. Process is
 * applying with {@link Processing#perform(java.lang.Object)} method. The
 * process is only calling if and only if
 * {@link Processing#accept(java.lang.Object)} return <code>true</code> for
 * inner {@link Rule}.<br><br>
 * <p>
 * Inner {@link Rule} is specifying with
 * {@link Processing#Processing(Rule)} constructor.
 * Default constructor implement an internal rule that's returning always true.
 *
 * @param <E> Element of the processing.
 * @author Tioben Neenot
 */
public abstract class Processing<E> implements Rule<E>
{

  /**
   * Rule using to validate accept(E e) method.
   */
  private Rule<E> rule;

  /**
   * Default constructor that's implementing a rule that's returning always
   * <code>true</code>.
   */
  public Processing()
  {
    this((Rule<E>) new True());
  }

  /**
   * Processing constructor with a specific rule.
   *
   * @param rule Rule to use that will be authorizing
   *             {@link Processing#perform(java.lang.Object)} method with
   *             {@link Processing#accept(java.lang.Object)}.
   * @throws NullPointerException If rule is <code>null</code>.
   */
  public Processing(Rule<E> rule)
  {
    if (States.isNullOrEmpty(rule))
    {
      throw new NullPointerException("Null rule");
    }

    this.rule = rule;
  }

  @Override
  public boolean accept(E element)
  {
    boolean _return = rule.accept(element);
    if (_return)
    {
      perform(element);
    }

    return _return;
  }

  /**
   * Applies a treatment for the element given as parameter.
   *
   * @param element Element on which the treatment will be applied.
   * @return <code>true</code> if treatment has been performed,
   * <code>false</code> if treatment was not necessary.
   */
  public abstract boolean perform(E element);
}
