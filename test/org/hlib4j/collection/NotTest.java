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
 * Unit tests for the {@link Not} class.
 *
 * @author Tioben Neenot
 */
public class NotTest
{

	/**
	 * Class reference for unit tests
	 */
	private Not< Integer > ref = null;

	/**
	 * Test initialisation
	 */
	@Before
	public void setUp()
	{
		ref = new Not<>( 5 );
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
	 * Test of accept method, of class {@link Not}. <br>
	 * <ul>
	 * <li><b>Description : </b>Controls if the value has excluded</li>
	 * <li><b>Result : </b>The value has excluded</li>
	 * <li><b>Comments : </b>None.</li>
	 * </ul>
	 */
	@Test
	public void testAccept()
	{
		Assert.assertTrue( ref.accept( 4 ) );
	}

	/**
	 * Test of accept method, of class {@link Not}. <br>
	 * <ul>
	 * <li><b>Description : </b>Controls if the value has excluded</li>
	 * <li><b>Result : </b>Value not excluded</li>
	 * <li><b>Comments : </b>None.</li>
	 * </ul>
	 */
	@Test
	public void testAcceptFalse()
	{
		Assert.assertFalse( ref.accept( 5 ) );
	}

	/**
	 * Test predicate with a specific method to find<br>
	 * <ul>
	 * <li><b>Description : </b>Build a Not predicate with a specific method for
	 * control value, builds another test instance class and test this last with
	 * Not predicate.</li>
	 * <li><b>Result : </b>PredicateMethod builds without error</li>
	 * <li><b>Comments: </b>None.</li>
	 * </ul>
	 *
	 * @throws java.lang.reflect.InvocationTargetException Exception that must not be ran during test to be available.
	 * @throws IllegalAccessException            Exception that must not be ran during test to be available.
	 * @see PredicateMethod
	 */
	@Test
	public void testNotWithMethod() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		Not< ForUTNotTest > _not = new Not<>( new ForUTNotTest( true ), "isFlag" );
		ForUTNotTest _f1 = new ForUTNotTest( true );
		ForUTNotTest _f2 = new ForUTNotTest( false );

		Assert.assertTrue( _not.accept( _f2 ) );
		Assert.assertFalse( _not.accept( _f1 ) );
	}
}

/**
 * Class for unit tests usage
 *
 * @author Tioben Neenot
 */
class ForUTNotTest
{

	/**
	 * A flag
	 */
	private final boolean aFlag;

	/**
	 * Constructor for ForUTNotTest
	 *
	 * @param aFlag Default value for test
	 */
	ForUTNotTest( boolean aFlag )
	{
		this.aFlag = aFlag;
	}

	/**
	 * Returns current flag value
	 *
	 * @return Current flag value
	 */
	public boolean isFlag()
	{
		return aFlag;
	}
}
