/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This program is free software; you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software
 *  Foundation; either version 2 of the License, or (at your option) any later
 *  version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 *  details.
 *
 *  You should have received a copy of the GNU General Public License along with
 *  this program; if not, write to the Free Software Foundation, Inc., 51
 *  Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package org.hlib4j.math;

/**
 * <code>CyclicCounter</code> class is a specific {@link Counter} where the current value come back to the initial
 * value while {@link Counter#increment()} match the {@link Counter#getUpperLimitValue()}. At the opposite, the upper
 * value is matching while the {@link Counter#decrement()} match the {@link Counter#getLowerLimitValue()}.
 */
public class CyclicCounter extends Counter
{
  /**
   * Builds an instance of the Counter by defining the counter limits and its specific default value.
   *
   * @param lowLimit     Low limit for this counter.
   * @param highLimit    High limit for this counter.
   * @param defaultValue Default value for this counter.
   * @throws RangeException If counter is not valid due to its parameters.
   */
  public CyclicCounter(Integer lowLimit, Integer highLimit, Integer defaultValue) throws RangeException
  {
    super(lowLimit, highLimit, defaultValue);
  }

  /**
   * Builds an instance of the Counter by defining specific limit and specific value.
   *
   * @param limitType    Limit type for this counter
   * @param lowLimit     Low limit for this counter.
   * @param highLimit    High limit for this counter.
   * @param defaultValue Default value for this counter.
   * @throws RangeException If counter is not valid due to its parameters.
   */
  public CyclicCounter(LimitType limitType, Integer lowLimit, Integer highLimit, Integer defaultValue) throws RangeException
  {
    super(limitType, lowLimit, highLimit, defaultValue);
  }

  /**
   * Builds an instance of the Counter by defining the counter limits.
   *
   * @param lowLimit  Low limit for this counter.
   * @param highLimit High limit for this Counter.
   * @throws RangeException If counter parameters are not valid
   */
  public CyclicCounter(Integer lowLimit, Integer highLimit) throws RangeException
  {
    super(lowLimit, highLimit);
  }

  /**
   * Increments the counter internal value by the specific step. This step can not be correspond to the last one
   * defined with {@link #setCounterStep(int)}.
   *
   * @param step Incrementation step value.
   * @return The new value after increment, or the value returned by {@link #getLowerLimitValue()}.
   */
  @Override
  public synchronized int incrementByStep(int step)
  {
    int new_value = super.incrementByStep(step);

    if (!isValid())
    {
      new_value = getLowerLimitValue();
    }

    return new_value;
  }

  /**
   * Decrements the counter internal value by the specific step. This step can not be correspond to the last one
   * defined with {@link #setCounterStep(int)}.
   *
   * @param step Decrementing step value.
   * @return The new value after decrement, or the value returned by {@link #getUpperLimitValue()}.
   */
  @Override
  public synchronized int decrementByStep(int step)
  {
    int new_Value = super.decrementByStep(step);

    if (!isValid())
    {
      new_Value = getUpperLimitValue();
    }

    return new_Value;
  }
}
