/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.collection;


import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Unit tests for the class {@link Multiple}.
 *
 * @author Tioben Neenot
 */
public class MultipleTest
{
  /**
   * Test of accept method, of class {@link Multiple}. <br>
   * <ul>
   * <li><b>Description : </b>Controls if the test value is conforming to the
   * ref class</li>
   * <li><b>Result : </b>Value conforms</li>
   * <li><b>Comments : </b>None.</li>
   * </ul>
   */
  @Test
  public void test_Accept_InvalidEvenValue_NotAccepted()
  {
    Assert.assertFalse(new Multiple<Integer>(4).accept(2));
  }

  /**
   * Test of accept method, of class {@link Multiple}. <br>
   * <ul>
   * <li><b>Description : </b>Controls if a bad value is not conforming to the
   * ref class</li>
   * <li><b>Result : </b>Bad value not conforms</li>
   * <li><b>Comments : </b>None.</li>
   * </ul>
   */
  @Test
  public void test_Accept_InvalidOddValue_NotAccepted()
  {
    Assert.assertFalse(new Multiple<Integer>(4).accept(3));
  }

  @Test(expected = NullPointerException.class)
  public void test_Accept_NullValue_NullPointerException()
  {
    new Multiple<Integer>(2).accept(null);
  }

  @Test
  public void test_Multiple_ForValidAtomicLongValue_Accepted()
  {
    Assert.assertTrue(new Multiple<AtomicLong>(new AtomicLong(4L)).accept(new AtomicLong(4l)));
  }

  @Test
  public void test_Multiple_ForInvalidAtomicLongValue_NotAccepted()
  {
    Assert.assertFalse(new Multiple<AtomicLong>(new AtomicLong(4L)).accept(new AtomicLong(3L)));
  }

  @Test
  public void test_Multiple_ForValidAtomicIntegerValue_Accepted()
  {
    Assert.assertTrue(new Multiple<AtomicInteger>(new AtomicInteger(3)).accept(new AtomicInteger(3)));
  }

  @Test
  public void test_Multiple_ForInvalidAtomicIntegerValue_NotAccepted()
  {
    Assert.assertFalse(new Multiple<AtomicInteger>(new AtomicInteger(3)).accept(new AtomicInteger(2)));
  }

  @Test
  public void test_Multiple_ForValidShortValue_Accepted()
  {
    Assert.assertTrue(new Multiple<Short>((short) 3).accept((short) 3));
  }

  @Test
  public void test_Multiple_ForInvalidShortValue_NotAccepted()
  {
    Assert.assertFalse(new Multiple<Short>((short) 3).accept((short) 2));
  }

  @Test
  public void test_Multiple_ForValidLongValue_Accepted()
  {
    Assert.assertTrue(new Multiple<Long>(3L).accept(3L));
  }

  @Test
  public void test_Multiple_ForInvalidLongValue_NotAccepted()
  {
    Assert.assertFalse(new Multiple<Long>(3L).accept(2L));
  }

  @Test
  public void test_Multiple_ForValidFloatValue_Accepted()
  {
    Assert.assertTrue(new Multiple<Float>(new Float(3.65f)).accept(3.65f));
  }

  @Test
  public void test_Multiple_ForInvalidFloatValue_NotAccepted()
  {
    Assert.assertFalse(new Multiple<Float>(new Float(3.65f)).accept(3.64f));
  }

  @Test
  public void test_Multiple_ForValidDoubleValue_Accepted()
  {
    Assert.assertTrue(new Multiple<Double>(new Double(2.5)).accept(new Double(2.5)));
  }

  @Test
  public void test_Multiple_ForInvalidDoubleValue_NotAccepted()
  {
    Assert.assertFalse(new Multiple<Double>(new Double(2.5)).accept(new Double(2.0)));
  }

  @Test
  public void test_Multiple_ForValidIntegerValue_Accepted()
  {
    Assert.assertTrue(new Multiple<Integer>(2).accept(2));
  }

  @Test
  public void test_Multiple_ForInvalidIntegerValue_NotAccepted()
  {
    Assert.assertFalse(new Multiple<Integer>(2).accept(3));
  }

  @Test
  public void test_Multiple_ForValidByteValue_Accepted()
  {
    Assert.assertTrue(new Multiple<Byte>((byte) 9).accept((byte) 9));
  }

  @Test
  public void test_Multiple_ForInvalidByteValue_NotAccepted()
  {
    Assert.assertFalse(new Multiple<Byte>((byte) 9).accept((byte) 8));
  }

  @Test
  public void test_Multiple_ForValidBigIntegerValue_Accepted()
  {
    Assert.assertTrue(new Multiple<BigInteger>(new BigInteger(new byte[]{1, 3})).accept(new BigInteger(new byte[]{
      1, 3})));
  }

  @Test
  public void test_Multiple_ForInvalidBigIntegerValue_NotAccepted()
  {
    Assert.assertFalse(new Multiple<BigInteger>(new BigInteger(new byte[]{1, 3})).accept(new BigInteger(new byte[]{
      1, 4})));
  }

  @Test
  public void test_Multiple_ForValidBigDecimalValue_Accepted()
  {
    Assert.assertTrue(new Multiple<BigDecimal>(new BigDecimal(5.2)).accept(new BigDecimal(5.2)));
  }

  @Test
  public void test_Multiple_ForInvalidBigDecimalValue_NotAccepted()
  {
    Assert.assertFalse(new Multiple<BigDecimal>(new BigDecimal(5.2)).accept(new BigDecimal(5.1)));
  }
}
