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

/**
 * Unit tests for the {@link Not} class.
 *
 * @author Tioben Neenot
 */
public class NotTest
{

  /**
   * Class reference for unit tests
   */
  private Not<Integer> ref = null;

  /**
   * Test initialisation
   */
  @Before
  public void setUp()
  {
    this.ref = new Not<>(5);
  }

  /**
   * Test cleaning
   */
  @After
  public void tearDown()
  {
    this.ref = null;
  }

  /**
   * Test of accept method, of class {@link Not}. <br>
   * <ul>
   * <li><b>Description : </b>Controls if the value has excluded</li>
   * <li><b>Result : </b>The value has excluded</li>
   * <li><b>Comments : </b>None.</li>
   * </ul>
   */
  @Test
  public void test_Accept_ValidValue_Accepted()
  {
    Assert.assertTrue(this.ref.accept(4));
  }

  /**
   * Test of accept method, of class {@link Not}. <br>
   * <ul>
   * <li><b>Description : </b>Controls if the value has excluded</li>
   * <li><b>Result : </b>Value not excluded</li>
   * <li><b>Comments : </b>None.</li>
   * </ul>
   */
  @Test
  public void test_Accept_InvalidValue_NotAccepted()
  {
    Assert.assertFalse(this.ref.accept(5));
  }

  @Test
  public void test_Accept_NullValue_Accepted()
  {
    Assert.assertTrue(this.ref.accept(null));
  }
}

