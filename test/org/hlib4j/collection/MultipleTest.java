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


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
	 * Class reference for tests
	 */
	private Multiple< Integer > ref = null;

	/**
	 * Test initialisation
	 */
	@Before
	public void setUp()
	{
		ref = new Multiple<>( 4 );
	}

	/**
	 * Test cleaning
	 */
	@After
	public void tearDown()
	{
		ref = null;
	}

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
    public void test_Accept_InvalidEvenValueNotAccepted() {
		Assert.assertFalse( ref.accept( 2 ) );
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
    public void test_Accept_InvalidOddValueNotAccepted() {
		Assert.assertFalse( ref.accept( 3 ) );
	}

    @Test(expected = NullPointerException.class)
    public void test_Accept_NullValueNotAccepted() {
        this.ref.accept((Integer) null);
    }

    @Test
    public void test_Multiple_ForValidAtomicLongValue() {
        Assert.assertTrue(new Multiple<AtomicLong>(new AtomicLong(4L)).accept(new AtomicLong(4l)));
    }

    @Test
    public void test_Multiple_ForValidAtomicIntegerValue() {
        Assert.assertTrue(new Multiple<AtomicInteger>(new AtomicInteger(3)).accept(new AtomicInteger(3)));
    }

    @Test
    public void test_Multiple_ForValidShortValue() {
        Assert.assertTrue(new Multiple<Short>((short) 3).accept((short) 3));
    }

    @Test
    public void test_Multiple_ForValidLongValue() {
        Assert.assertTrue(new Multiple<Long>(3L).accept(3L));
    }

    @Test
    public void test_Multiple_ForValidFloatValue() {
        Assert.assertTrue(new Multiple<Float>(new Float(3.65f)).accept(3.65f));
    }

    @Test
    public void test_Multiple_ForValidDoubleValue() {
        Assert.assertTrue(new Multiple<Double>(new Double(2.5)).accept(new Double(2.5)));
    }

    @Test
    public void test_Multiple_ForValidIntegerValue() {
        Assert.assertTrue(new Multiple<Integer>(2).accept(2));
    }

    @Test
    public void test_Multiple_ForValidByteValue() {
        Assert.assertTrue(new Multiple<Byte>((byte) 9).accept((byte) 9));
    }

    @Test
    public void test_Multiple_ForValidBigIntegerValue() {
        Assert.assertTrue(new Multiple<BigInteger>(new BigInteger(new byte[]{1, 3})).accept(new BigInteger(new byte[]{
                1, 3})));
    }

    @Test
    public void test_Multiple_ForValidBigDecimalValue() {
        Assert.assertTrue(new Multiple<BigDecimal>(new BigDecimal(5.2)).accept(new BigDecimal(5.2)));
    }

    @Test
    public void test_Multiple_ForInvalidAtomicLongValue() {
        Assert.assertFalse(new Multiple<AtomicLong>(new AtomicLong(4L)).accept(new AtomicInteger(3)));
    }

    @Test
    public void test_Multiple_ForInvalidAtomicIntegerValue() {
        Assert.assertFalse(new Multiple<AtomicInteger>(new AtomicInteger(3)).accept(new AtomicLong(4L)));
    }

    @Test
    public void test_Multiple_ForInvalidShortValue() {
        Assert.assertFalse(new Multiple<Short>((short) 3).accept(3));
    }

    @Test
    public void test_Multiple_ForInvalidLongValue() {
        Assert.assertFalse(new Multiple<Long>(3L).accept(3.3f));
    }

    @Test
    public void test_Multiple_ForInvalidFloatValue() {
        Assert.assertFalse(new Multiple<Float>(new Float(3.65f)).accept(3));
    }

    @Test
    public void test_Multiple_ForInvalidDoubleValue() {
        Assert.assertFalse(new Multiple<Double>(new Double(2.5)).accept(1));
    }

    @Test
    public void test_Multiple_ForInvalidIntegerValue() {
        Assert.assertFalse(new Multiple<Integer>(2).accept(2.3f));
    }

    @Test
    public void test_Multiple_ForInvalidByteValue() {
        Assert.assertFalse(new Multiple<Byte>((byte) 9).accept(9));
    }

    @Test
    public void test_Multiple_ForInvalidBigIntegerValue() {
        Assert.assertFalse(new Multiple<BigInteger>(new BigInteger(new byte[]{1, 3})).accept(1));
    }

    @Test
    public void test_Multiple_ForInvalidBigDecimalValue() {
        Assert.assertFalse(new Multiple<BigDecimal>(new BigDecimal(5.2)).accept(5.3f));
    }
}
