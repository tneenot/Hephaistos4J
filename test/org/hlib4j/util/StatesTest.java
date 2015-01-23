package org.hlib4j.util;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * Unit tests for {@link org.hlib4j.util.StatesTest} class.
 *
 * @author Tioben Neenot
 */
public class StatesTest
{

	public StatesTest()
	{
	}

	@Before
	public void setUp()
	{
	}

	@After
	public void tearDown()
	{
	}

	/**
	 * Test: <b>Test if an object is <code>null</code> or empty.</b>.<br>
	 * <ul>
	 * <li>Input data: <code>null</code> value.</li>
	 * <li>Result: <code>true</code>.</li>
	 * <li>Method: isNullOrEmpty(...)</li>
	 * </ul>
	 * Note: <i>None</i>
	 */
	@Test
	public void testIsNullOrEmpty()
	{
		Assert.assertTrue( States.isNullOrEmpty( null ) );
	}

	/**
	 * Test: <b>Test if a string is <code>null</code> or empty.</b>.<br>
	 * <ul>
	 * <li>Input data: empties string.</li>
	 * <li>Result: <code>true</code>.</li>
	 * <li>Method: isNullOrEmpty(...)</li>
	 * </ul>
	 * Note: <i>None</i>
	 */
	@Test
	public void testIsNullOrEmptyString()
	{
		Assert.assertTrue( States.isNullOrEmpty( "" ) );
		Assert.assertTrue( States.isNullOrEmpty( " " ) );
	}

	/**
	 * Test: <b>Test an object instance and controls if it's <code>null</code> or empty.</b>.<br>
	 * <ul>
	 * <li>Input data: an Object instance.</li>
	 * <li>Result: <code>false</code>.</li>
	 * <li>Method: isNullOrEmpty(...)</li>
	 * </ul>
	 * Note: <i>Result is <code>false</code> since the {@link Object#toString()} is never empty.</i>
	 */
	@Test
	public void testIsNullOrEmptyObject()
	{
		Assert.assertFalse( States.isNullOrEmpty( new Object() ) );
	}

	/**
	 * Test: <b>Test an empty collection.</b>.<br>
	 * <ul>
	 * <li>Input data: an empty collection.</li>
	 * <li>Result: <code>true</code>.</li>
	 * <li>Method: isNullOrEmpty(...)</li>
	 * </ul>
	 * Note: <i>None.</i>
	 */
	@Test
	public void testIsNullOrEmptyCollection()
	{
		Collection< Object > c = new ArrayList<>();
		Assert.assertTrue( States.isNullOrEmpty( c ) );
	}

	/**
	 * Test: <b>test if an array of <code>long</code> types is <code>null</code> or empty.</b>.<br>
	 * <ul>
	 * <li>Input data: empties array of <code>long</code> types.</li>
	 * <li>Result: <code>true</code>.</li>
	 * <li>Method: isNullOrEmpty(...)</li>
	 * </ul>
	 * Note: <i>None</i>
	 */
	@Test
	public void testIsNullOrEmptyArrayLong()
	{
		long[] _larray1 = null;
		Assert.assertTrue( States.isNullOrEmptyArray( _larray1 ) );

		long[] _larray2 =
			{
			};
		Assert.assertTrue( States.isNullOrEmptyArray( _larray2 ) );
	}

	/**
	 * Test: <b>test if an array of <code>double</code> types is <code>null</code> or empty.</b>.<br>
	 * <ul>
	 * <li>Input data: empties array of <code>double</code> types.</li>
	 * <li>Result: <code>true</code>.</li>
	 * <li>Method: isNullOrEmpty(...)</li>
	 * </ul>
	 * Note: <i>None</i>
	 */
	@Test
	public void testIsNullOrEmptyArrayDouble()
	{
		double[] _darray1 = null;
		Assert.assertTrue( States.isNullOrEmptyArray( _darray1 ) );

		double[] _darray2 =
			{
			};
		Assert.assertTrue( States.isNullOrEmptyArray( _darray2 ) );
	}

	/**
	 * Test: <b>test if an array of <code>float</code> types is <code>null</code> or empty.</b>.<br>
	 * <ul>
	 * <li>Input data: empties array of <code>float</code> types.</li>
	 * <li>Result: <code>true</code>.</li>
	 * <li>Method: isNullOrEmptyArray(...)</li>
	 * </ul>
	 * Note: <i>None</i>
	 */
	@Test
	public void testIsNullOrEmptyArrayFloat()
	{
		float[] _farray1 = null;
		Assert.assertTrue( States.isNullOrEmptyArray( _farray1 ) );

		float[] _farray2 =
			{
			};
		Assert.assertTrue( States.isNullOrEmptyArray( _farray2 ) );
	}

	/**
	 * Test: <b>test if an array of <code>short</code> types is <code>null</code> or empty.</b>.<br>
	 * <ul>
	 * <li>Input data: empties array of <code>short</code> types.</li>
	 * <li>Result: <code>true</code>.</li>
	 * <li>Method: isNullOrEmptyArray(...)</li>
	 * </ul>
	 * Note: <i>None</i>
	 */
	@Test
	public void testIsNullOrEmptyArrayShort()
	{
		short[] _sarray1 = null;
		Assert.assertTrue( States.isNullOrEmptyArray( _sarray1 ) );

		short[] _sarray2 =
			{
			};
		Assert.assertTrue( States.isNullOrEmptyArray( _sarray2 ) );
	}

	/**
	 * Test: <b>test if an array of <code>byte</code> types is <code>null</code> or empty.</b>.<br>
	 * <ul>
	 * <li>Input data: empties array of <code>byte</code> types.</li>
	 * <li>Result: <code>true</code>.</li>
	 * <li>Method: isNullOrEmptyArray(...)</li>
	 * </ul>
	 * Note: <i>None</i>
	 */
	@Test
	public void testIsNullOrEmptyArrayByte()
	{
		byte[] _bbarray1 = null;
		Assert.assertTrue( States.isNullOrEmptyArray( _bbarray1 ) );

		byte[] _bbarray2 =
			{
			};
		Assert.assertTrue( States.isNullOrEmptyArray( _bbarray2 ) );
	}

	/**
	 * Test: <b>test if an array of <code>char</code> types is <code>null</code> or empty.</b>.<br>
	 * <ul>
	 * <li>Input data: empties array of <code>char</code> types.</li>
	 * <li>Result: <code>true</code>.</li>
	 * <li>Method: isNullOrEmptyArray(...)</li>
	 * </ul>
	 * Note: <i>None</i>
	 */
	@Test
	public void testIsNullOrEmptyArrayChar()
	{
		char[] _carray1 = null;
		Assert.assertTrue( States.isNullOrEmptyArray( _carray1 ) );

		char[] _carray2 =
			{
			};
		Assert.assertTrue( States.isNullOrEmptyArray( _carray2 ) );
	}

	/**
	 * Test: <b>test if an array of <code>boolean</code> types is <code>null</code> or empty.</b>.<br>
	 * <ul>
	 * <li>Input data: empties array of <code>boolean</code> types.</li>
	 * <li>Result: <code>true</code>.</li>
	 * <li>Method: isNullOrEmptyArray(...)</li>
	 * </ul>
	 * Note: <i>None</i>
	 */
	@Test
	public void testNullOrEmptyArrayBoolean()
	{
		boolean[] _barray1 = null;
		Assert.assertTrue( States.isNullOrEmptyArray( _barray1 ) );

		boolean[] _barray2 =
			{
			};
		Assert.assertTrue( States.isNullOrEmptyArray( _barray2 ) );
	}

	/**
	 * Test: <b>test if an array of <code>int</code> types is <code>null</code> or empty.</b>.<br>
	 * <ul>
	 * <li>Input data: empties array of <code>int</code> types.</li>
	 * <li>Result: <code>true</code>.</li>
	 * <li>Method: isNullOrEmptyArray(...)</li>
	 * </ul>
	 * Note: <i>None</i>
	 */
	@Test
	public void testIsNullOrEmptyArrayInt()
	{
		int[] _iarray1 = null;
		Assert.assertTrue( States.isNullOrEmptyArray( _iarray1 ) );

		int[] _iarray2 =
			{
			};
		Assert.assertTrue( States.isNullOrEmptyArray( _iarray2 ) );
	}

	/**
	 * Test: <b>test if an array of bases types is <code>null</code> or empty.</b>.<br>
	 * <ul>
	 * <li>Input data: non empties array of bases types.</li>
	 * <li>Result: <code>false</code>.</li>
	 * <li>Method: isNullOrEmptyArray(...)</li>
	 * </ul>
	 * Note: <i>None</i>
	 */
	@Test
	public void testIsNullOrEmptyArray2()
	{

		float[] _farray =
			{
				2.1f
			};
		Assert.assertFalse( States.isNullOrEmptyArray( _farray ) );

		double[] _darray =
			{
				2.3
			};
		Assert.assertFalse( States.isNullOrEmptyArray( _darray ) );

		long[] _larray =
			{
				Long.MIN_VALUE
			};
		Assert.assertFalse( States.isNullOrEmptyArray( _larray ) );
	}

	/**
	 * Test: <b>test if an array of <code>short</code> types is <code>null</code> or empty.</b>.<br>
	 * <ul>
	 * <li>Input data: non empties array of <code>short</code> types.</li>
	 * <li>Result: <code>false</code>.</li>
	 * <li>Method: isNullOrEmptyArray(...)</li>
	 * </ul>
	 * Note: <i>None</i>
	 */
	@Test
	public void testIsNullOrEmptyArray2Short()
	{
		short[] _sarray =
			{
				2
			};
		Assert.assertFalse( States.isNullOrEmptyArray( _sarray ) );
	}

	/**
	 * Test: <b>test if an array of <code>byte</code> types is <code>null</code> or empty.</b>.<br>
	 * <ul>
	 * <li>Input data: non empties array of <code>byte</code> types.</li>
	 * <li>Result: <code>false</code>.</li>
	 * <li>Method: isNullOrEmptyArray(...)</li>
	 * </ul>
	 * Note: <i>None</i>
	 */
	@Test
	public void testIsNullOrEmptyArray2Byte()
	{
		byte[] _bbarray =
			{
				1
			};
		Assert.assertFalse( States.isNullOrEmptyArray( _bbarray ) );
	}

	/**
	 * Test: <b>test if an array of <code>char</code> types is <code>null</code> or empty.</b>.<br>
	 * <ul>
	 * <li>Input data: non empties array of <code>char</code> types.</li>
	 * <li>Result: <code>false</code>.</li>
	 * <li>Method: isNullOrEmptyArray(...)</li>
	 * </ul>
	 * Note: <i>None</i>
	 */
	@Test
	public void testIsNullOrEmptyArray2Char()
	{
		char[] _carray =
			{
				'c'
			};
		Assert.assertFalse( States.isNullOrEmptyArray( _carray ) );
	}

	/**
	 * Test: <b>test if an array of <code>boolean</code> types is <code>null</code> or empty.</b>.<br>
	 * <ul>
	 * <li>Input data: non empties array of <code>boolean</code> types.</li>
	 * <li>Result: <code>false</code>.</li>
	 * <li>Method: isNullOrEmptyArray(...)</li>
	 * </ul>
	 * Note: <i>None</i>
	 */
	@Test
	public void testIsNullOrEmptyArray2Boolean()
	{
		boolean[] _barray =
			{
				false
			};
		Assert.assertFalse( States.isNullOrEmptyArray( _barray ) );
	}

	/**
	 * Test: <b>test if an array of <code>int</code> types is <code>null</code> or empty.</b>.<br>
	 * <ul>
	 * <li>Input data: non empties array of <code>int</code> types.</li>
	 * <li>Result: <code>false</code>.</li>
	 * <li>Method: isNullOrEmptyArray(...)</li>
	 * </ul>
	 * Note: <i>None</i>
	 */
	@Test
	public void testIsNullOrEmptyArray2Int()
	{
		int[] _iarray =
			{
				0
			};
		Assert.assertFalse( States.isNullOrEmptyArray( _iarray ) );
	}

	/**
	 * Test: <b>Test if an array of objects is null or empty.</b>.<br>
	 * <ul>
	 * <li>Input data: an empty array of different kinds of object.</li>
	 * <li>Result: <code>true</code>.</li>
	 * <li>Method: isNullOrEmptyArray(...)</li>
	 * </ul>
	 * Note: <i>Test for all arrays type only.</i>
	 */
	@Test
	public void testIsNullOrEmptyArrayOfObjects()
	{
		Object[] o = null;
		Assert.assertTrue( States.isNullOrEmptyArray( o ) );

		Object[] o2 =
			{
			};
		Assert.assertTrue( States.isNullOrEmptyArray( o2 ) );

		Integer[] _i = null;
		Assert.assertTrue( States.isNullOrEmptyArray( _i ) );

		Integer[] _iarray =
			{
			};
		Assert.assertTrue( States.isNullOrEmptyArray( _iarray ) );
	}

	/**
	 * Test: <b>Test a valid assertion and controls the parameters is correctly returned.</b>.<br>
	 * <ul>
	 * <li>Input data: a valid <code>Date</code> type.</li>
	 * <li>Result: <code>true</code> and <code>Date</code> type returned.</li>
	 * <li>Method: <code>validate(...)</code></li>
	 * </ul>
	 * Note: <i>None</i>
	 */
	@Test
	public void testValidate()
	{
		Date d = Calendar.getInstance().getTime();
		Date r = States.validate( d );

		Assert.assertSame( d, r );
		Assert.assertEquals( d, r );
	}

	/**
	 * Test: <b>Test a valid assertion for a primary type.</b>.<br>
	 * <ul>
	 * <li>Input data: an integer</li>
	 * <li>Result: no error and integer value returned.</li>
	 * <li>Method: <code>validate(...)</code></li>
	 * </ul>
	 * Note: <i>None</i>
	 */
	@Test
	public void testValidatePrimaryType()
	{
		int i = Integer.MAX_VALUE;
		int r = States.validate( i );

		Assert.assertEquals( r, i );
	}

	/**
	 * Test: <b>Test the assertion for a non empty array of primaries types</b>.<br>
	 * <ul>
	 * <li>Input data: array of primaries types.</li>
	 * <li>Result: <code>true</code> and array not modified.</li>
	 * <li>Method: <code>validateArray(...)</code>.</li>
	 * </ul>
	 * Note: <i>None.</i>
	 */
	@Test
	public void testValidatePrimaryArrayType()
	{
		int[] i =
			{
				1, 2, 3, 4
			};
		int[] r = States.validate( i );

		Assert.assertArrayEquals( r, i );
	}

	/**
	 * Test: <b>Test assert for a <code>null</code> array.</b>.<br>
	 * <ul>
	 * <li>Input data: null primary array.</li>
	 * <li>Result: an exception</li>
	 * <li>Method: <code>validate(...)</code></li>
	 * </ul>
	 * Note: <i>None.</i>
	 */
	@Test( expected = AssertionError.class )
	public void testValidateNullArray()
	{
		int[] i = null;
		States.validate( i );
	}

	/**
	 * Test: <b>Test assert for a <code>null</code> array.</b>.<br>
	 * <ul>
	 * <li>Input data: null primary array.</li>
	 * <li>Result: an exception</li>
	 * <li>Method: <code>validateArray(...)</code></li>
	 * </ul>
	 * Note: <i>None.</i>
	 */
	@Test( expected = AssertionError.class )
	public void testValidateArrayNull()
	{
		int[] i = null;
		States.validate( i );
	}

	/**
	 * Test: <b>Test assert for an empty array.</b>.<br>
	 * <ul>
	 * <li>Input data: empty primary array.</li>
	 * <li>Result: an exception</li>
	 * <li>Method: <code>validateArray(...)</code></li>
	 * </ul>
	 * Note: <i>None.</i>
	 */
	@Test( expected = AssertionError.class )
	public void testValidateEmptyArray()
	{
		Integer[] i =
			{
			};
		States.validateArray( i );
	}

	/**
	 * Test: <b>Test assert for an empty array.</b>.<br>
	 * <ul>
	 * <li>Input data: empty primary array.</li>
	 * <li>Result: an exception</li>
	 * <li>Method: <code>validateArray(...)</code></li>
	 * </ul>
	 * Note: <i>None.</i>
	 */
	@Test( expected = AssertionError.class )
	public void testValidateEmptyIntArray()
	{
		int[] i =
			{
			};
		States.validateArray(i);
	}

	/**
	 * Test: <b>Test assert for an empty array.</b>.<br>
	 * <ul>
	 * <li>Input data: empty primary array.</li>
	 * <li>Result: an exception</li>
	 * <li>Method: <code>validateArray(...)</code></li>
	 * </ul>
	 * Note: <i>None.</i>
	 */
	@Test( expected = AssertionError.class )
	public void testValidateEmptyShortArray()
	{
		short[] s =
			{
			};
		States.validateArray( s );
	}

	/**
	 * Test: <b>Test assert for an empty array.</b>.<br>
	 * <ul>
	 * <li>Input data: empty primary array.</li>
	 * <li>Result: an exception</li>
	 * <li>Method: <code>validateArray(...)</code></li>
	 * </ul>
	 * Note: <i>None.</i>
	 */
	@Test( expected = AssertionError.class )
	public void testValidateEmptyLongArray()
	{
		long[] l =
			{
			};
		States.validateArray( l );
	}

	/**
	 * Test: <b>Test assert for an empty array.</b>.<br>
	 * <ul>
	 * <li>Input data: empty primary array.</li>
	 * <li>Result: an exception</li>
	 * <li>Method: <code>validateArray(...)</code></li>
	 * </ul>
	 * Note: <i>None.</i>
	 */
	@Test( expected = AssertionError.class )
	public void testValidateEmptyBooleanArray()
	{
		boolean[] b =
			{
			};
		States.validateArray( b );
	}

	/**
	 * Test: <b>Test assert for an empty array.</b>.<br>
	 * <ul>
	 * <li>Input data: empty primary array.</li>
	 * <li>Result: an exception</li>
	 * <li>Method: <code>validateArray(...)</code></li>
	 * </ul>
	 * Note: <i>None.</i>
	 */
	@Test( expected = AssertionError.class )
	public void testValidateEmptyFloatArray()
	{
		float[] f =
			{
			};
		States.validateArray( f );
	}

	/**
	 * Test: <b>Test assert for an empty array.</b>.<br>
	 * <ul>
	 * <li>Input data: empty primary array.</li>
	 * <li>Result: an exception</li>
	 * <li>Method: <code>validateArray(...)</code></li>
	 * </ul>
	 * Note: <i>None.</i>
	 */
	@Test( expected = AssertionError.class )
	public void testValidateEmptyDoubleArray()
	{
		double[] d =
			{
			};
		States.validateArray( d );
	}

	/**
	 * Test: <b>Test assert for an empty array.</b>.<br>
	 * <ul>
	 * <li>Input data: empty primary array.</li>
	 * <li>Result: an exception</li>
	 * <li>Method: <code>validateArray(...)</code></li>
	 * </ul>
	 * Note: <i>None.</i>
	 */
	@Test( expected = AssertionError.class )
	public void testValidateEmptyCharArray()
	{
		char[] c =
			{
			};
		States.validateArray( c );
	}

	/**
	 * Test: <b>Test assert for an empty array.</b>.<br>
	 * <ul>
	 * <li>Input data: empty primary array.</li>
	 * <li>Result: an exception</li>
	 * <li>Method: <code>validateArray(...)</code></li>
	 * </ul>
	 * Note: <i>None.</i>
	 */
	@Test( expected = AssertionError.class )
	public void testValidateEmptyByteArray()
	{
		byte[] b =
			{
			};
		States.validateArray( b );
	}

	/**
	 * Test: <b>Test an invalid assertion with an invalid value.</b>.<br>
	 * <ul>
	 * <li>Input data: <code>null</code> value type.</li>
	 * <li>Result: <code>AssertionException</code>.</li>
	 * <li>Method: <code>validate(...)</code></li>
	 * </ul>
	 * Note: <i>None</i>
	 */
	@Test( expected = AssertionError.class )
	public void testValidateError()
	{
		States.validate( null );
	}
}
