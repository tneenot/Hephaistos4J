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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * Unit tests for <code>ClassDefinition</code> class.
 *
 * @author Tioben Neenot
 */
public class ClassDefinitionTest
{

	/**
	 * Properties list
	 */
	private PropertiesFactorFake properties = null;


	@Before
	public void setUp() throws Exception
	{
		properties = new PropertiesFactorFake();
	}


	@After
	public void tearDown() throws Exception
	{
		properties = null;
	}

	/**
	 * Unit test <code>SetPropertyNullName()</code>.
	 * <ul>
	 * <li>Description: sets a null property name.</li>
	 * <li>Result: Throws an exception.</li>
	 * <li>Comment: none.</li>
	 * </ul>
	 *
	 * @throws java.lang.reflect.InvocationTargetException     Exception that must not be ran for the test must be available.
	 * @throws UnsupportedOperationException Exception that must not be ran for the test must be available.
	 */
	@Test( expected = IllegalArgumentException.class )
    public final void test_SetProperty_NullName() throws IllegalArgumentException, InvocationTargetException,
            UnsupportedOperationException
	{
		properties.setPropertyValue( null, "toto" );
	}

	/**
	 * Unit test <code>SetPropertyEmptyName()</code>.
	 * <ul>
	 * <li>Description: Sets an empty property name.</li>
	 * <li>Result: Throws an exception.</li>
	 * <li>Comment: property name is "" form.</li>
	 * </ul>
	 *
	 * @throws java.lang.reflect.InvocationTargetException     Exception that must not be ran for the test must be available.
	 * @throws UnsupportedOperationException Exception that must not be ran for the test must be available.
	 */
	@Test( expected = IllegalArgumentException.class )
    public final void test_SetProperty_EmptyName_1stForm() throws IllegalArgumentException, InvocationTargetException,
            UnsupportedOperationException
	{
		properties.setPropertyValue("", null);
	}

	/**
	 * Unit test <code>SetPropertyEmptyName2()</code>.
	 * <ul>
	 * <li>Description: Sets an empty string as property name.</li>
	 * <li>Result: <i>result awaiting here</i>.</li>
	 * <li>Comment: property name is " " form.</li>
	 * </ul>
	 *
	 * @throws java.lang.reflect.InvocationTargetException     Exception that must not be ran for the test must be available.
	 * @throws UnsupportedOperationException Exception that must not be ran for the test must be available.
	 */
	@Test( expected = IllegalArgumentException.class )
    public final void test_SetProperty_EmptyName_2dForm() throws IllegalArgumentException, InvocationTargetException,
            UnsupportedOperationException
	{
		properties.setPropertyValue( " ", null );
	}

	/**
	 * Unit test <code>SetPropertyUknown()</code>.
	 * <ul>
	 * <li>Description: Sets a non existent property value.</li>
	 * <li>Result: Throws an exception.</li>
	 * <li>Comment: none.</li>
	 * </ul>
	 *
	 * @throws java.lang.reflect.InvocationTargetException     Exception that must not be ran for the test must be available.
	 * @throws UnsupportedOperationException Exception that must not be ran for the test must be available.
	 */
	@Test( expected = InvocationTargetException.class )
    public final void test_SetProperty_UnknownName() throws IllegalArgumentException, InvocationTargetException,
            UnsupportedOperationException
	{
		properties.setPropertyValue("toto", 1);
	}

	/**
	 * Unit test <code>SetPropertyValidReadWrite()</code>.
	 * <ul>
	 * <li>Description: Sets a value for a read/write property.</li>
	 * <li>Result: property updated.</li>
	 * <li>Comment: none.</li>
	 * </ul>
	 *
	 * @throws IllegalArgumentException      Exception that must not be ran for the test must be available.
	 * @throws java.lang.reflect.InvocationTargetException     Exception that must not be ran for the test must be available.
	 * @throws UnsupportedOperationException Exception that must not be ran for the test must be available.
	 */
	@Test
    public final void test_SetProperty_ValidReadWrite() throws IllegalArgumentException, InvocationTargetException,
            UnsupportedOperationException
	{
		properties.setPropertyValue( "age", 10 );

		Assert.assertEquals(10, properties.getPropertyValue("age"));
	}

	/**
	 * Unit test <code>SetPropertyValidReadOnly()</code>.
	 * <ul>
	 * <li>Description: Sets a value for a readonly property .</li>
	 * <li>Result: Throws an exception.</li>
	 * <li>Comment: none.</li>
	 * </ul>
	 *
	 * @throws java.lang.reflect.InvocationTargetException     Exception that must not be ran for the test must be available.
	 * @throws UnsupportedOperationException Exception that must not be ran for the test must be available.
	 */
	@Test( expected = UnsupportedOperationException.class )
    public final void test_SetProperty_ValidReadOnly() throws IllegalArgumentException, InvocationTargetException,
            UnsupportedOperationException
	{
		properties.setPropertyValue("numSecu", "999");
	}

	/**
	 * Unit test <code>GetProperty()</code>.
	 * <ul>
	 * <li>Description: Gets the default property value.</li>
	 * <li>Result: Default value conforms.</li>
	 * <li>Comment: none.</li>
	 * </ul>
	 *
	 * @throws java.lang.reflect.InvocationTargetException Exception that must not be ran for the test must be available.
	 * @throws IllegalArgumentException  Exception that must not be ran for the test must be available.
	 */
	@Test
    public final void test_GetProperty_ValidName() throws IllegalArgumentException, InvocationTargetException {
		Assert.assertEquals("1234567890", properties.getPropertyValue("numSecu"));
	}

	/**
	 * Unit test <code>GetPropertyUnvalid()</code>.
	 * <ul>
	 * <li>Description: Gets the value for a non existent property.</li>
	 * <li>Result: Throws an exception.</li>
	 * <li>Comment: none.</li>
	 * </ul>
	 *
	 * @throws IllegalArgumentException  Exception that must not be ran for the test must be available.
	 * @throws java.lang.reflect.InvocationTargetException Exception that must not be ran for the test must be available.
	 */
	@Test( expected = InvocationTargetException.class )
    public final void test_GetProperty_InvalidName() throws IllegalArgumentException, InvocationTargetException {
		properties.getPropertyValue( "toto" );
	}

	/**
	 * Unit test <code>GetPropertyNull()</code>.
	 * <ul>
	 * <li>Description: Gets a value for a null property name.</li>
	 * <li>Result: Throws an exception.</li>
	 * <li>Comment: none.</li>
	 * </ul>
	 *
	 * @throws IllegalArgumentException  Exception that must not be ran for the test must be available.
	 * @throws java.lang.reflect.InvocationTargetException Exception that must not be ran for the test must be available.
	 */
	@Test( expected = IllegalArgumentException.class )
    public final void test_GetProperty_NullName() throws IllegalArgumentException, InvocationTargetException {
		properties.getPropertyValue(null);
	}

	/**
	 * Unit test <code>GetProperties()</code>.
	 * <ul>
	 * <li>Description: Gets the properties list.</li>
	 * <li>Result: Properties list not empty.</li>
	 * <li>Comment: none.</li>
	 * </ul>
	 */
	@Test
    public final void test_GetProperties_ValidList() {
		Assert.assertNotNull( properties.getProperties() );
	}

	/**
	 * Unit test <code>GetPropertiesList()</code>.
	 * <ul>
	 * <li>Description: Gets each content of the properties list.</li>
	 * <li>Result: No null content.</li>
	 * <li>Comment: none.</li>
	 * </ul>
	 */
	@Test
    public final void test_GetProperties_ValidListElement() {
		for ( Property p : properties.getProperties() )
		{
			System.out.println( p );
			Assert.assertNotNull( p );
		}
	}

	/**
	 * Unit test <code>GetPropertiesAdd()</code>.
	 * <ul>
	 * <li>Description: Add a new property from the properties list.</li>
	 * <li>Result: Throws an exception.</li>
	 * <li>Comment: none.</li>
	 * </ul>
	 *
	 * @throws UnsupportedOperationException Exception test awaiting
	 */
	@Test( expected = UnsupportedOperationException.class )
    public final void test_GetProperties_AddProperty() {
		properties.getProperties().add( new Property( "Toto", 5, true ) );
	}

	/**
	 * Unit test <code>GetPropertiesAndModify()</code>.
	 * <ul>
	 * <li>Description: Modify the value for each read/write property.</li>
	 * <li>Result: All read/write properties updated.</li>
	 * <li>Comment: none.</li>
	 * </ul>
	 *
	 * @throws IllegalAccessException If access exception error.
	 */
	@Test
    public final void test_GetProperties_ModifyProperty() throws IllegalAccessException {
		// Modify all properties in read/write mode
		for ( Property p : properties.getProperties() )
		{
			if (!p.isReadOnly())
			{
				p.setValue( 10 );
			}
		}

		for ( Property p : properties.getProperties() )
		{
			if (!p.isReadOnly())
			{
				Assert.assertNotNull( p.getValue() );
			}
		}
	}

	/**
	 * Test of toString method, of class ClassDefinition.
	 */
	@Test
    public void test_ToString_DescriptionClass() {
		ClassDefinition instance = new PropertiesFactorFake();
		String expResult = "ClassName";
		String result = instance.toString();
		Assert.assertEquals( expResult, result );
	}

	// ----------------------------------------------------------
	// Internal classes for tests
	// ----------------------------------------------------------

	/**
	 * Implementation class for properties management
	 *
	 * @author Tioben Neenot
	 */
	class PropertiesFactorFake extends ClassDefinition
	{

		PropertiesFactorFake()
		{
			properties.put( "age", new Property( "age", false ) );
			properties.put( "name", new Property( "name", false ) );
			properties.put( "numSecu", new Property( "numSecu", "1234567890", true ) );
		}

		@Override
		public String getName()
		{
			return "ClassName";
		}
	}
}
