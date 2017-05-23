package org.hlib4j.collection;
/*
*  Hephaistos 4 Java library: a library with facilities to get more concise code.
*  
*  Copyright (C) 2015 Tioben Neenot
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
*  
*/

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

