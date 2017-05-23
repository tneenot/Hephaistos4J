/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.collection;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link True} class.
 *
 * @author Tioben Neenot
 */
public class TrueTest
{


  /**
   * Test of accept method, of class True.
   */
  @Test
  public void test_Accept_AnyValue_ValidValue()
  {
    Object e = null;
    True instance = new True();
    boolean expResult = true;
    boolean result = instance.accept(e);
    assertEquals(expResult, result);
  }
}