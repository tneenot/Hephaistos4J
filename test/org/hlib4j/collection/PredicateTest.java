/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.collection;

import org.hlib4j.util.RandomGenerator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link Predicate} class.
 *
 * @author Tioben Neenot
 */
public class PredicateTest
{

  private RandomGenerator randomData;

  private int randomValue;

  private Predicate<Integer> refOccurrenceTested;

  @Before
  public void setUp()
  {
    this.randomData = new RandomGenerator();
    this.randomValue = this.randomData.getIsolatedValue();
    this.refOccurrenceTested = new Predicate<>(this.randomValue);
  }

  @After
  public void tearDown()
  {
    this.refOccurrenceTested = null;
    this.randomValue = 0;
    this.randomData = null;
  }

  @Test
  public void test_Accept_ValidValue_Accepted()
  {
    assertTrue(this.refOccurrenceTested.accept(this.randomValue));
  }

  @Test
  public void test_Accept_InvalidValue_NotAccepted()
  {
    Assert.assertFalse(this.refOccurrenceTested.accept(this.randomData.getIsolatedValue()));
  }

  @Test
  public final void test_Accept_InvalidObjectType_NotAccepted()
  {
    Predicate<Object> _ref = new Predicate<>(null);
    Assert.assertFalse(_ref.accept(new Object()));
  }

  @Test
  public final void test_Accept_ValidNullValue_Accepted()
  {
    Predicate<Object> _ref = new Predicate<>(null);
    Assert.assertTrue(_ref.accept(null));
  }

  @Test
  public final void test_Accept_InvalidNullValue_NotAccepted()
  {
    Assert.assertFalse(this.refOccurrenceTested.accept(null));
  }
}

