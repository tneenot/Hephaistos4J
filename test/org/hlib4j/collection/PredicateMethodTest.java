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

import java.lang.reflect.InvocationTargetException;

/**
 * Unit tests for {@link PredicateMethod} class.
 *
 * @author Tioben Neenot
 */
public class PredicateMethodTest
{

	/**
	 * Class reference for tests
	 */
	private PredicateMethod<AUTFake> ref = null;

	/**
	 * Tests initialisation
	 */
	@Before
	public void setUp()
	{
		try
		{
			ref = new PredicateMethod<>( new AUTFake( 5 ), "getValue" );
		}
		catch ( IllegalArgumentException | IllegalAccessException | InvocationTargetException e )
		{
			Assert.fail( e.getMessage() );
		}
	}

	/**
	 * Tests cleaning
	 */
	@After
	public void tearDown()
	{
		ref = null;
	}

	/**
	 * Test of exception of class PredicateMethod.<br>
	 * <ul>
	 * <li><b>Description: </b>Build a predicate with a bad method name.</li>
	 * <li><b>Result : </b>Exception throw.</li>
	 * <li><b>Comments: </b>None.</li>
	 * </ul>
	 *
	 * @throws IllegalAccessException            Exception that must not be ran for the test must be available.
	 * @throws java.lang.reflect.InvocationTargetException Exception that must not be ran for the test must be available.
	 * @see PredicateMethod#PredicateMethod(Object, String)
	 */
	@Test( expected = ClassCastException.class )
    public void test_WithInvocationTargetException() throws IllegalArgumentException, IllegalAccessException,
            InvocationTargetException
	{
		new PredicateMethod<>( new AUTFake( 1 ), "foo" );
	}

	/**
	 * Test of accept method, of class PredicateMethod. <br>
	 * <ul>
	 * <li><b>Description : </b>Sets a value for the {@link PredicateMethod#accept(Object)} and control it</li>
	 * <li><b>Result : </b>Value conforms</li>
	 * <li><b>Comments : </b>None.</li>
	 * </ul>
	 */
	@Test
    public void test_Accept_ValidValueAccepted() {
		AUTFake e = new AUTFake( 5 );
        Assert.assertTrue(ref.accept(e));
    }

	/**
	 * Test of accept method, of class PredicateMethod. <br>
	 * <ul>
	 * <li><b>Description : </b>Sets a value for the {@link PredicateMethod#accept(Object) } and control a different
	 * value</li>
	 * <li><b>Result : </b>Value not conforms</li>
	 * <li><b>Comments : </b>None.</li>
	 * </ul>
	 */
	@Test
    public void test_Accept_InvalidValueNotAccepted() {
		AUTFake e = new AUTFake( 4 );
		Assert.assertFalse( ref.accept( e ) );
	}

	/**
	 * Test method for {@link PredicateMethod#accept(Object)}.
	 * <ul>
	 * <li><b>Description: </b>Controls if the given value is valid or not</li>
	 * <li><b>Results: </b>Value conforms</li>
	 * <li><b>Comments: </b>None.</li>
	 * </ul>
	 */
	@Test(expected = NullPointerException.class)
    public final void test_Accept_NullPointerException() throws InvocationTargetException, IllegalAccessException {
        new PredicateMethod<>( null, null );
	}

}

/**
 * Class for unit tests
 *
 * @author Tioben Neenot
 */
class AUTFake
{

	/**
	 * The class value
	 */
	private final int value;

	/**
	 * The class constructor
	 *
	 * @param value The value for this class
	 */
	AUTFake(int value)
	{
		this.value = value;
	}

	/**
	 * Gets the class value
	 *
	 * @return The class value
	 */
	public int getValue()
	{
		return value;
	}
}
