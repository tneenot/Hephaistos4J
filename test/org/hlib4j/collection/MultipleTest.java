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
	public void testAccept()
	{
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
	public void testAcceptFalse()
	{
		Assert.assertFalse( ref.accept( 3 ) );
	}

	/**
	 * Test of accept method, of class {@link Multiple}. <br>
	 * 
	 * <ul>
	 * <li><b>Description : </b>Builds a {@link Multiple} for several types and
	 * control if accept return <code>true</code> for another instance of same
	 * type.</li>
	 * <li><b>Result : </b>Return <code>true</code> for same type.</li>
	 * <li><b>Comments: </b>None.</li>
	 * </ul>
	 */
	@Test
	public void testMultipleAcceptTrue()
	{
		Assert.assertTrue( new Multiple< BigDecimal >( new BigDecimal( 5.2 ) ).accept( new BigDecimal( 5.2 ) ) );
		Assert.assertTrue( new Multiple< BigInteger >( new BigInteger( new byte[] { 1, 3 } ) ).accept( new BigInteger( new byte[] {
			1, 3 } ) ) );
		Assert.assertTrue( new Multiple< Byte >( ( byte ) 9 ).accept( ( byte ) 9 ) );
		Assert.assertTrue( new Multiple< Integer >( 2 ).accept( 2 ) );
		Assert.assertTrue( new Multiple< Double >( new Double( 2.5 ) ).accept( new Double( 2.5 ) ) );
		Assert.assertTrue( new Multiple< Float >( new Float( 3.65f ) ).accept( 3.65f ) );
		Assert.assertTrue( new Multiple< Long >( 3L ).accept( 3L ) );
		Assert.assertTrue( new Multiple< Short >( ( short ) 3 ).accept( ( short ) 3 ) );
		Assert.assertTrue( new Multiple< AtomicInteger >( new AtomicInteger( 3 ) ).accept( new AtomicInteger( 3 ) ) );
		Assert.assertTrue( new Multiple< AtomicLong >( new AtomicLong( 4L ) ).accept( new AtomicLong( 4l ) ) );
	}

	/**
	 * Test of accept method, of class {@link Multiple}.<br>
	 * 
	 * <ul>
	 * <li><b>Description : </b>Builds a {@link Multiple} for several types and
	 * control if accept return <code>false</code> for a different type.</li>
	 * <li><b>Comments: </b>None.</li>
	 * </ul>
	 */
	@Test
	public void testMultipleAcceptFalse()
	{
		Assert.assertFalse( new Multiple< BigDecimal >( new BigDecimal( 5.2 ) ).accept( 5.3f ) );
		Assert.assertFalse( new Multiple< BigInteger >( new BigInteger( new byte[] { 1, 3 } ) ).accept( 1 ) );
		Assert.assertFalse( new Multiple< Byte >( ( byte ) 9 ).accept( 9 ) );
		Assert.assertFalse( new Multiple< Integer >( 2 ).accept( 2.3f ) );
		Assert.assertFalse( new Multiple< Double >( new Double( 2.5 ) ).accept( 1 ) );
		Assert.assertFalse( new Multiple< Float >( new Float( 3.65f ) ).accept( 3 ) );
		Assert.assertFalse( new Multiple< Long >( 3L ).accept( 3.3f ) );
		Assert.assertFalse( new Multiple< Short >( ( short ) 3 ).accept( 3 ) );
		Assert.assertFalse( new Multiple< AtomicInteger >( new AtomicInteger( 3 ) ).accept( new AtomicLong( 3L ) ) );
		Assert.assertFalse( new Multiple< AtomicLong >( new AtomicLong( 4L ) ).accept( new AtomicInteger( 4 ) ) );
	}
}
