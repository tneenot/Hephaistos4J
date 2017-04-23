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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link CyclicCounter} class.
 */
public class CyclicCounterTest
{
  private Counter counter;

  @Before
  public void setUp() throws RangeException
  {
    counter = new CyclicCounter(0, 10, 1);
  }

  @Test
  public void test_incrementByStep_IncrementToLastValue_UpperLimitValue() throws Exception
  {
    Assert.assertEquals(counter.getUpperLimitValue().intValue() - 1, counter.incrementByStep(8));
  }

  @Test
  public void test_decrementByStep_DecrementToFirstValue_LowerLimitValue() throws Exception
  {
    Assert.assertEquals(counter.getLowerLimitValue().intValue(), counter.decrementByStep(1));
  }

  @Test
  public void test_increment_AfterIncrementToUpperLimitValue_LowerLimitValue()
  {
    counter.incrementByStep(8);
    Assert.assertEquals(counter.getLowerLimitValue().intValue(), counter.increment());
  }

  @Test
  public void test_decrement_AfterDecrementToLowerLimitValue_UpperLimitValue()
  {
    counter.decrementByStep(1);
    Assert.assertEquals(counter.getUpperLimitValue().intValue(), counter.decrement());
  }

}