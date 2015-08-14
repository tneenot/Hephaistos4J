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

import org.junit.Assert;
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
    public void test_IsNullOrEmptyArray_OnNullValue_NullValueConfirmed() {
		Assert.assertTrue( States.isNullOrEmptyArray(null) );
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
    public void test_IsNullOrEmpty_OnEmptyStringNaturalForm_StringEmpty() {
		Assert.assertTrue( States.isNullOrEmpty( "" ) );
	}

    @Test
    public void test_IsNullOrEmpty_OnEmptyStringVariantForm_StringEmpty() {
        Assert.assertTrue(States.isNullOrEmpty(" "));
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
    public void test_IsNullOrEmpty_OnNonNullObject_ObjectNotNull() {
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
    public void test_IsNullOrEmpty_OnEmptyCollection_EmptyCollection() {
		Collection< Object > c = new ArrayList<>();
		Assert.assertTrue( States.isNullOrEmpty( c ) );
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
    public void test_NullOrEmptyArray_OnNullBoolean_NullBooleanConfirmed() {
        Boolean[] _boolean_array = null;
        Assert.assertTrue(States.isNullOrEmptyArray(_boolean_array));
    }

    @Test
    public void test_NullOrEmptyArray_OnEmptyBooleanArray_EmptyArrayConfirmed() {
        Boolean[] _boolean_array = {};
        Assert.assertTrue(States.isNullOrEmptyArray(_boolean_array));
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
    public void test_Validate_OnSameInstances_SameInstancesConfirmed() {
		Date d = Calendar.getInstance().getTime();
		Date r = States.validate( d );

		Assert.assertSame( d, r );
	}

    @Test
    public void test_Validate_OnEqualsInstances_EqualInstancesConfirmed() {
        Date d = Calendar.getInstance().getTime();
        Date r = States.validate(d);

        Assert.assertEquals(d, r);
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
    public void test_Validate_PrimaryIntType_ValueEquals() {
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
    public void test_Validate_PrimaryArrayType_ArraysEquals() {
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
    public void test_Validate_NullArray_AssertionError() {
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
    public void test_ValidateArray_EmptyArray_AssertionError() {
		Integer[] i = {	};
		States.validateArray( i );
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
    public void test_Validate_NullValue_AssertionError() {
		States.validate( null );
	}

    @Test
    public void test_ValidateNotNullOnly_OnEmptyArray_ArraysValidate() throws Exception {
        Object[] o = {};
        Assert.assertArrayEquals(o, States.validateNotNullOnly(o));
    }

    @Test
    public void test_ValidateNotNullOnly_NoNullInteger_ValueValidate() throws Exception {

        Integer i = 4;
        Assert.assertEquals(i, States.validateNotNullOnly(i));
    }
}
