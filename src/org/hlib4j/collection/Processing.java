/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 * This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.collection;

import org.hlib4j.util.States;

import java.util.function.Predicate;

/**
 * Runs a process on a specific value given as argument. The Processing class is
 * using within a collection for which a predicate is applying. Process is
 * applying with {@link Processing#perform(java.lang.Object)} method. The
 * process is only calling if and only if
 * {@link Processing#test(java.lang.Object)} return <code>true</code> for
 * inner predicate.<br><br>
 * <p>
 * Inner predicate is specifying with
 * {@link Processing#Processing(java.util.function.Predicate)} constructor.
 * Default constructor is a convenient constructor that implements an internal predicate that returns
 * always true. That means, the {@link org.hlib4j.collection.Processing#perform(Object)} will apply on all elements of the underlying collection.
 *
 * @param <E> Element of the processing.
 * @author Tioben Neenot
 */
public abstract class Processing<E> implements Predicate<E>
{

  /**
   * Predicate for this processing class.
   */
  private Predicate<E> predicate = null;

  /**
   * Default constructor that's implementing a predicate that's returning always
   * {@code true}.
   */
  public Processing()
  {
    this(p -> true);
  }

  /**
   * Builds an instance of the Processing class with a predicate value
   *
   * @param predicate Predicate value
   */
  public Processing(Predicate<E> predicate)
  {
    this.predicate = States.validate(predicate);
  }

  /**
   * Evaluates this predicate on the given argument.
   *
   * @param e the input argument
   * @return {@code true} if the input argument matches the predicate,
   * otherwise {@code false}
   */
  @Override
  public boolean test(E e)
  {

    return this.predicate.test(e);

  }

  /**
   * Applies a treatment for the element given as parameter.
   *
   * @param e Element on which the treatment will be applied.
   * @return The result of the perform task.
   */
  public abstract boolean perform(E e);
}
