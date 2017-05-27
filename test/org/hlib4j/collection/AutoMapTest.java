/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.collection;

import org.hlib4j.concept.KeyGenerator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

/**
 * Unit tests for {@link AutoMap} class.
 */
public class AutoMapTest
{
  private AutoMap<Integer, String> autoMap;

  @Before
  public void setUp() throws Exception
  {
    autoMap = new AutoMap<>(new KeyGenerator<Integer>()
    {
      public int counter;

      @Override
      public Integer generateNewKey()
      {
        return counter++;
      }
    }, new HashMap<Integer, String>());
  }

  @Test
  public void test_put_PutSingleValue_SingleValueAccepted() throws Exception
  {
    this.autoMap.put("foo");

    Assert.assertEquals("foo", autoMap.getAssociatedMap().get(0));
  }

  @Test
  public void test_put_PutSameValueTwice_SameValueRecordedTwice() throws Exception
  {
    this.autoMap.put("foo");
    this.autoMap.put("foo");

    Assert.assertEquals("foo", autoMap.getAssociatedMap().get(0));
    Assert.assertEquals("foo", autoMap.getAssociatedMap().get(1));
  }

  @Test
  public void test_getAssociatedMap_PutSingleValue_ContainsOneValue() throws Exception
  {
    this.autoMap.put("foo");

    Assert.assertEquals(1, this.autoMap.getAssociatedMap().size());
  }

  @Test
  public void test_getAssociatedMap_PutSameValueTwice_ContainsTwoValues() throws Exception
  {
    this.autoMap.put("foo");
    this.autoMap.put("foo");

    Assert.assertEquals(2, this.autoMap.getAssociatedMap().size());
  }

  @Test
  public void test_toString_StringRepresentation_StringRepresentationNotNull() throws Exception
  {
    Assert.assertNotNull(this.autoMap.toString());
  }

  @After
  public void tearDown() throws Exception
  {
    this.autoMap.getAssociatedMap().clear();
  }

}