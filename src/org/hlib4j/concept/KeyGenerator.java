/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.concept;

/**
 * Abstract class that defines a method to generate a new key.
 *
 * @param <T> The type of the generated value.
 */
public abstract class KeyGenerator<T>
{
  /**
   * Generate a new key value.
   *
   * @return A new key value.
   */
  public abstract T generateNewKey();

  @Override
  public String toString()
  {
    final StringBuilder sb = new StringBuilder("KeyGenerator{");
    sb.append('}');
    return sb.toString();
  }
}
