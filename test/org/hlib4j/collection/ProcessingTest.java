/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.collection;

import org.hlib4j.concept.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link Processing} class.
 *
 * @author Tioben Neenot
 */
public class ProcessingTest
{
  /**
   * Test of accept method, of class Processing.
   */
  @Test
  public void test_Accept_ValidValue_Accepted()
  {
    Object e = new Object();
    Processing<Object> instance = new ProcessingFake<>();
    boolean expResult = true;
    boolean result = instance.accept(e);
    assertEquals(expResult, result);
  }

  /**
   * Test of perform method, of class Processing.
   */
  @Test
  public void test_Perform_ValidValue_Performed()
  {
    Object e = new Object();
    Processing<Object> instance = new ProcessingFake<>();
    boolean expResult = true;
    boolean result = instance.perform(e);
    assertEquals(expResult, result);
  }

  /**
   * Test of perform method, of class Processing.
   */
  @Test
  public void test_Perform_InvalidValue_NotPerformed()
  {
    True e = new True();
    Processing<True> instance = new ProcessingFake<>(new Not<True>(e));
    boolean expResult = false;
    boolean result = instance.accept(e);
    assertEquals(expResult, result);
  }

  /**
   * Test of perform method, of class Processing.
   */
  @Test
  public void test_getCount_ValidObject_IndirectPerformCalled()
  {
    Object e = new Object();
    Processing<Object> instance = new ProcessingFake<>();
    instance.accept(e);
    assertEquals(1, ((ProcessingFake<Object>) instance).getCount());
  }

  @Test(expected = NullPointerException.class)
  public void test_Constructor_NullRule_NullPointerException()
  {
    new ProcessingFake<Object>(null);
  }


  /**
   * Implementation class for unit tests.
   */
  class ProcessingFake<E> extends Processing<E>
  {

    /**
     * Count if perform is calling or not.
     */
    private int count = 0;

    public ProcessingFake()
    {
      super();
    }

    public ProcessingFake(Rule<E> r)
    {
      super(r);
    }

    @Override
    public boolean perform(Object element)
    {
      ++count;

      return element != null;

    }

    public int getCount()
    {
      return count;
    }
  }
}