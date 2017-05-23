/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * Unit tests for {@link PredicateMethod} class.
 *
 * @author Tioben Neenot
 */
public class PredicateMethodTest
{

  /**
   * Class reference for tests
   */
  private PredicateMethod<AUTFake> ref = null;

  /**
   * Tests initialisation
   */
  @Before
  public void setUp()
  {
    try
    {
      ref = new PredicateMethod<>(new AUTFake(5), "getValue");
    } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e)
    {
      Assert.fail(e.getMessage());
    }
  }

  /**
   * Tests cleaning
   */
  @After
  public void tearDown()
  {
    ref = null;
  }

  /**
   * Test of exception of class PredicateMethod.<br>
   * <ul>
   * <li><b>Description: </b>Build a predicate with a bad method name.</li>
   * <li><b>Result : </b>Exception throw.</li>
   * <li><b>Comments: </b>None.</li>
   * </ul>
   *
   * @throws IllegalAccessException                      Exception that must not be ran for the test must be available.
   * @throws java.lang.reflect.InvocationTargetException Exception that must not be ran for the test must be available.
   * @see PredicateMethod#PredicateMethod(Object, String)
   */
  @Test(expected = InvocationTargetException.class)
  public void test_Constructor_InvalidMethodName_InvocationTargetException() throws IllegalArgumentException, IllegalAccessException,
    InvocationTargetException
  {
    new PredicateMethod<>(new AUTFake(1), "foo");
  }

  /**
   * Test of accept method, of class PredicateMethod. <br>
   * <ul>
   * <li><b>Description : </b>Sets a value for the {@link PredicateMethod#accept(Object)} and control it</li>
   * <li><b>Result : </b>Value conforms</li>
   * <li><b>Comments : </b>None.</li>
   * </ul>
   */
  @Test
  public void test_Accept_ValidValue_Accepted()
  {
    AUTFake e = new AUTFake(5);
    Assert.assertTrue(ref.accept(e));
  }

  /**
   * Test of accept method, of class PredicateMethod. <br>
   * <ul>
   * <li><b>Description : </b>Sets a value for the {@link PredicateMethod#accept(Object) } and control a different
   * value</li>
   * <li><b>Result : </b>Value not conforms</li>
   * <li><b>Comments : </b>None.</li>
   * </ul>
   */
  @Test
  public void test_Accept_InvalidValue_NotAccepted()
  {
    AUTFake e = new AUTFake(4);
    Assert.assertFalse(ref.accept(e));
  }

  /**
   * Test method for {@link PredicateMethod#accept(Object)}.
   * <ul>
   * <li><b>Description: </b>Controls if the given value is valid or not</li>
   * <li><b>Results: </b>Value conforms</li>
   * <li><b>Comments: </b>None.</li>
   * </ul>
   */
  @Test(expected = NullPointerException.class)
  public final void test_Constructor_NullParameters_NullPointerException() throws InvocationTargetException, IllegalAccessException
  {
    new PredicateMethod<>(null, null);
  }

}

/**
 * Class for unit tests
 *
 * @author Tioben Neenot
 */
class AUTFake
{

  /**
   * The class value
   */
  private final int value;

  /**
   * The class constructor
   *
   * @param value The value for this class
   */
  AUTFake(int value)
  {
    this.value = value;
  }

  /**
   * Gets the class value
   *
   * @return The class value
   */
  public int getValue()
  {
    return value;
  }
}
