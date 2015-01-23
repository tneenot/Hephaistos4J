package org.hlib4j.concept;
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
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Units tests for the <code>PropertyManager</code> class.
 *
 * @author Tioben Neenot
 * @see PropertyManager
 */
public class ClassImplementationTest
{

	/**
	 * Instance class reference
	 */
	private PropertyManager instance = null;

	/**
	 * Test initialization
	 *
	 * @throws InstantiationException Exception that must not be ran for the test must be available.
	 */
	@Before
	public void setUp() throws InstantiationException
	{
		instance = new PropertyManager( "ClassRef" );
	}

	/**
	 * Test cleaning
	 */
	@After
	public void tearDown()
	{
		instance = null;
	}

	/**
	 * Test of getName method, of class PropertyManager.
	 */
	@Test
	public void getName()
	{
		String expResult = "ClassRef";
		String result = instance.getName();
		assertEquals( expResult, result );
	}

	/**
	 * Test of the PropertyManager constructor
	 *
	 * @throws InstantiationException Exception that must not be ran for the test must be available.
	 */
	@Test( expected = InstantiationException.class )
	public void createInstanceWithNullName() throws InstantiationException
	{
		new PropertyManager( null );
	}

	/**
	 * Test of the PropertyManager constructor
	 *
	 * @throws InstantiationException Exception that must not be ran for the test must be available.
	 */
	@Test( expected = InstantiationException.class )
	public void createInstanceWithEmptyName() throws InstantiationException
	{
		new PropertyManager( "" );
	}

	/**
	 * Test of the PropertyManager constructor
	 *
	 * @throws InstantiationException Awaiting exception
	 */
	@Test( expected = InstantiationException.class )
	public void createInstanceWithEmptyName2() throws InstantiationException
	{
		new PropertyManager( "  " );
	}

	/**
	 * Test of the PropertyManager constructor
	 *
	 * @throws InstantiationException Exception that must not be ran for the test must be available.
	 */
	@Test( expected = InstantiationException.class )
	public void createInstanceWithSpaceInName() throws InstantiationException
	{
		new PropertyManager( "Class Ref" );
	}

	/**
	 * Test of getName method, of class PropertyManager.
	 */
	@Test
	public void testGetName()
	{
		String expResult = "ClassRef";
		String result = instance.getName();
		assertEquals( expResult, result );
	}

	/**
	 * Test of Add method, of class PropertyManager.
	 *
	 * @throws java.lang.reflect.InvocationTargetException Exception that must not be ran for the test must be available.
	 */
	@Test
	public void testAdd() throws IllegalArgumentException, InvocationTargetException
	{
		Property p = new Property( "prop", 1, false );
		instance.Add( p );

		assertNotNull( instance.getPropertyValue( "prop" ) );
	}
}
