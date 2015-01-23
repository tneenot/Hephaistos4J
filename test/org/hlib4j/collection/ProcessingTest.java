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
import org.junit.*;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link Processing} class.
 *
 * @author Tioben Neenot
 */
public class ProcessingTest
{

	public ProcessingTest()
	{
	}

	@BeforeClass
	public static void setUpClass()
	{
	}

	@AfterClass
	public static void tearDownClass()
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
	 * Test of accept method, of class Processing.
	 */
	@Test
	public void testAccept()
	{
		System.out.println( "accept" );
		Object e = new Object();
		Processing< Object > instance = new ProcessingImpl< Object >();
		boolean expResult = true;
		boolean result = instance.accept( e );
		assertEquals( expResult, result );
	}

	/**
	 * Test of perform method, of class Processing.
	 */
	@Test
	public void testPerformTrue()
	{
		System.out.println( "perform true" );
		Object e = new Object();
		Processing< Object > instance = new ProcessingImpl< Object >();
		boolean expResult = true;
		boolean result = instance.perform( e );
		assertEquals( expResult, result );
	}

	/**
	 * Test of perform method, of class Processing.
	 */
	@Test
	public void testPerformTrue2()
	{
		System.out.println( "perform true second form" );
		True e = new True();
		Processing< True > instance = new ProcessingImpl<>( new Not< True >( e ) );
		boolean expResult = false;
		boolean result = instance.accept( e );
		assertEquals( expResult, result );
		assertEquals( 0, ( ( ProcessingImpl< True > ) instance ).getCount() );
	}

	/**
	 * Test of perform method, of class Processing.
	 */
	@Test
	public void testPerformTrue3()
	{
		System.out.println( "perform true third form" );
		Object e = new Object();
		Processing< Object > instance = new ProcessingImpl< Object >();
		boolean expResult = true;
		boolean result = instance.accept( e );
		assertEquals( expResult, result );
		assertEquals( 1, ( ( ProcessingImpl< Object > ) instance ).getCount() );
	}

	/**
	 * Test of perform method, of class Processing.
	 */
	@Test
	public void testPerformFalse()
	{
		System.out.println( "perform false" );
		Object e = null;
		Processing< Object > instance = new ProcessingImpl< Object >();
		boolean expResult = false;
		boolean result = instance.perform( e );
		assertEquals( expResult, result );
	}

	/**
	 * Implementation class for unit tests.
	 */
	class ProcessingImpl < E > extends Processing< E >
	{

		/**
		 * Count if perform is calling or not.
		 */
		private int count = 0;

		public ProcessingImpl()
		{
			super();
		}

		public ProcessingImpl( Rule< E > r )
		{
			super( r );
		}

		@Override
		public boolean perform( Object e )
		{
			++count;

			return e != null;

		}

		public int getCount()
		{
			return count;
		}
	}
}