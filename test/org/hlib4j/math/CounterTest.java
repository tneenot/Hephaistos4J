/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.math;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link Counter} class.
 */
public class CounterTest
{

  private Counter counterTesting;
  private Integer initialValue;

  @Before
  public void setUp() throws Exception
  {
    counterTesting = new Counter(0, 10);
    initialValue = this.counterTesting.getCurrentValue();

  }

  @Test
  public void test_Increment_ValidValue_ValueUpdated() throws Exception
  {
    Assert.assertEquals(initialValue + 1, counterTesting.increment());
  }

  @Test(expected = RangeException.class)
  public void test_Increment_MoreThanUpperLimit_RangeException() throws RangeException
  {
    // Setup
    Counter _local_counter = new Counter(1, 1, 1);

    // SUT
    _local_counter.increment();
  }

  @Test
  public void test_IncrementByStep_ValidValue_ValueUpdated() throws Exception
  {
    Assert.assertEquals(initialValue + 5, counterTesting.incrementByStep(5));
  }

  @Test
  public void test_IncrementByStep_MoreThanUpperLimit_ValueNotUpdated()
  {
    // Setup
    int _value = counterTesting.incrementByStep(counterTesting.getUpperLimitValue() + 1);

    // Assert
    Assert.assertEquals(_value, this.counterTesting.incrementByStep(this.counterTesting.getUpperLimitValue() + 1));
  }

  @Test
  public void test_Decrement_ValidValue_ValueUpdated() throws Exception
  {
    // Setup
    Counter _local_counter = new Counter(-5, 5, 0);

    // Assert
    Assert.assertEquals(-1, _local_counter.decrement());
  }

  @Test
  public void test_Decrement_LessThanLowerLimit_ValueNotUpdated()
  {
    // Setup
    int _value = this.counterTesting.decrement();

    // Assert
    Assert.assertEquals(_value, this.counterTesting.decrement());
  }

  @Test
  public void test_DecrementByStep_ValidValue_ValueUpdated() throws Exception
  {
    // Setup
    Counter _local_counter = new Counter(-5, 5, 0);

    // Assert
    Assert.assertEquals(-2, _local_counter.decrementByStep(2));
  }

  @Test
  public void test_SetCounterStep_SetNewValue_ValueUpdated() throws Exception
  {
    this.counterTesting.setCounterStep(5);
    Assert.assertEquals(5, this.counterTesting.getCounterStep());
  }

  @Test
  public void test_SetCounterStep_SetNewValue_NextIncrementValueUpdated() throws Exception
  {
    // Setup
    int _value = this.counterTesting.getCurrentValue();

    // SUT
    this.counterTesting.setCounterStep(3);

    // Assert
    Assert.assertEquals(_value + 3, this.counterTesting.increment());
  }

  @Test
  public void test_GetCounterStep_DefaultValue_AwaitingDefaultStep() throws Exception
  {
    Assert.assertEquals(1, this.counterTesting.getCounterStep());
  }

  @Test(expected = RangeException.class)
  public void test_SetCounterStep_ValueMoreThanAvailableLimit_RangeException() throws RangeException
  {
    this.counterTesting.setCounterStep(this.counterTesting.getUpperLimitValue() + 1);
  }

  @Test(expected = RangeException.class)
  public void test_SetCounterStep_NotEnoughValue_RangeException() throws RangeException
  {
    // Setup
    Counter _local_counter = new Counter(0, 4);

    // SUT
    int _value = _local_counter.increment();

    // Inner assert
    _local_counter.setCounterStep(3);
  }

  @Test
  public void test_Increment_IncrementAndDecrement_OriginalValueRetreive() throws RangeException
  {
    // Setup
    int _original_value = this.counterTesting.getCurrentValue();

    // SUT
    this.counterTesting.increment();
    this.counterTesting.decrement();

    // Assert
    Assert.assertEquals(_original_value, this.counterTesting.getCurrentValue().intValue());
  }

  @Test
  public void test_IsValid_DefaultValue_CounterIsValid()
  {
    Assert.assertTrue(this.counterTesting.isValid());
  }

  @Test
  public void test_IsValid_LimitsNotReached_CounterIsValid() throws RangeException
  {
    // Setup
    this.counterTesting.increment();

    // Assert
    Assert.assertTrue(this.counterTesting.isValid());
  }

  @Test
  public void test_IsValid_UpperLimitReached_CounterNotValid()
  {
    // Setup
    this.setupCounterToUpperLimit();

    // Assert
    Assert.assertFalse(this.counterTesting.isValid());
  }

  private void setupCounterToUpperLimit()
  {
    try
    {
      this.counterTesting.setCurrentValue(this.counterTesting.getUpperLimitValue());
      Assert.fail("This point couldn't be reached");
    } catch (RangeException e)
    {
      // Awaiting point due to setup constraint
    }
  }

  @Test
  public void test_IsValid_LowerLimitReached_CounterNotValid()
  {
    // Setup
    this.setupCounterToOverloadLowerLimit();

    // Assert
    Assert.assertFalse(this.counterTesting.isValid());
  }

  private void setupCounterToOverloadLowerLimit()
  {
    try
    {
      this.counterTesting.setCurrentValue(this.counterTesting.getLowerLimitValue() - 1);
      Assert.fail("This point couldn't be reached");
    } catch (RangeException e)
    {
      // Awaiting point due to setup constraint
    }
  }

  @Test
  public void test_rearm_forInvalidCounter_CounterRearmedAndValid()
  {
    // Setup
    this.setupCounterToUpperLimit();

    // Assert
    Assert.assertTrue(this.counterTesting.rearm());
  }

  @Test
  public void test_rearm_forInvalidCounter_DefaultValueUpdated()
  {
    // Setup
    this.setupCounterToUpperLimit();

    // SUT
    this.counterTesting.rearm();

    // Assert
    Assert.assertEquals(this.counterTesting.getLowerLimitValue(), this.counterTesting.getCurrentValue());
  }

  @Test
  public void test_rearm_forValidCounter_CounterRearmedAndValid()
  {
    // Setup
    this.counterTesting.increment();

    // Assert
    Assert.assertTrue(this.counterTesting.rearm());
  }

  @Test
  public void test_rearm_forValidCounter_DefaultValueUpdated()
  {
    // Setup
    this.counterTesting.incrementByStep(5);

    // SUT
    this.counterTesting.rearm();

    // Assert
    Assert.assertEquals(this.counterTesting.getLowerLimitValue(), this.counterTesting.getCurrentValue());
  }

  @Test
  public void test_invalidate_InvalidateCounter_CounterNoValid()
  {
    this.counterTesting.rearm();
    this.counterTesting.invalidate();

    Assert.assertFalse(this.counterTesting.isValid());
  }

  @Test
  public void test_getCurrentValue_InvalidateCounter_LowerLimitValue()
  {
    this.counterTesting.invalidate();

    Assert.assertEquals(this.counterTesting.getCurrentValue(), this.counterTesting.getLowerLimitValue());
  }


  @Test
  public void test_isValid_InvalidateAndRearmCounter_CounterIsValid()
  {
    this.counterTesting.invalidate();
    this.counterTesting.rearm();

    Assert.assertTrue(this.counterTesting.isValid());
  }

  @After
  public void tearDown() throws Exception
  {
    initialValue = null;
    counterTesting = null;
  }
}