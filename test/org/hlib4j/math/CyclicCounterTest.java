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