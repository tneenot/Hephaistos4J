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

	@Test( expected = IllegalArgumentException.class )
	public final void test_SetProperty_WithNullName_IllegalArgumentException() throws IllegalArgumentException, InvocationTargetException,
			UnsupportedOperationException
	{
		properties.setPropertyValue( null, "toto" );
	}

	@Test( expected = IllegalArgumentException.class )
    public final void test_SetProperty_EmptyNameWithNaturalForm_IllegalArgumentException() throws IllegalArgumentException,
            InvocationTargetException,
            UnsupportedOperationException
	{
		properties.setPropertyValue("", null);
	}

	@Test( expected = IllegalArgumentException.class )
    public final void test_SetProperty_EmptyNameWithVariantForm_IllegalArgumentException() throws IllegalArgumentException,
            InvocationTargetException,
            UnsupportedOperationException
	{
		properties.setPropertyValue( " ", null );
	}

	@Test( expected = InvocationTargetException.class )
	public final void test_SetProperty_UnknownName_InvocationTargetException() throws IllegalArgumentException, InvocationTargetException,
			UnsupportedOperationException
	{
		properties.setPropertyValue("toto", 1);
	}

	@Test
    public final void test_SetProperty_ForReadWrite_PropertyUpdated() throws IllegalArgumentException, InvocationTargetException,
            UnsupportedOperationException
	{
		properties.setPropertyValue( "age", 10 );

		Assert.assertEquals(10, properties.getPropertyValue("age"));
	}

	@Test( expected = UnsupportedOperationException.class )
    public final void test_SetProperty_ForReadOnly_UnsupportedOperationException() throws IllegalArgumentException,
            InvocationTargetException,
            UnsupportedOperationException
	{
		properties.setPropertyValue("numSecu", "999");
	}

	@Test
    public final void test_GetProperty_ForValidName_ValidValue() throws IllegalArgumentException, InvocationTargetException {
		Assert.assertEquals("1234567890", properties.getPropertyValue("numSecu"));
	}

	@Test( expected = InvocationTargetException.class )
	public final void test_GetProperty_InvalidName_InvocationTargetException() throws IllegalArgumentException,
			InvocationTargetException {
		properties.getPropertyValue( "toto" );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void test_GetProperty_NullName_IllegalArgumentException() throws IllegalArgumentException,
            InvocationTargetException {
		properties.getPropertyValue(null);
	}

	@Test
    public final void test_GetProperties_ValidList_NotNull() {
		Assert.assertNotNull( properties.getProperties() );
	}

	@Test
    public final void test_GetProperties_ValidListElement_NotNull() {
		for ( Property p : properties.getProperties() )
		{
			Assert.assertNotNull( p );
		}
	}

	@Test( expected = UnsupportedOperationException.class )
	public final void test_GetProperties_AddPropertyForbidden_UnsupportedOperationException() {
		properties.getProperties().add( new Property( "Toto", 5, true ) );
	}

	@Test
	public final void test_GetProperties_UpdatePropertyValue_PropertyUpdated() throws IllegalAccessException {
		// Modify all properties in read/write mode
		setupValueForReadWriterProperty();

		for ( Property p : properties.getProperties() )
		{
			if (!p.isReadOnly())
			{
				Assert.assertNotNull( p.getValue() );
			}
		}
	}

	private void setupValueForReadWriterProperty() {
		for ( Property p : properties.getProperties() )
		{
			if (!p.isReadOnly())
			{
				p.setValue( 10 );
			}
		}
	}

	@Test
    public void test_ToString_DescriptionClass_ValidDescription() {
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
