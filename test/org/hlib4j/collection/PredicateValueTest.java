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

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link org.hlib4j.collection.PredicateValue} class.
 *
 * @author Tioben Neenot
 */
public class PredicateValueTest
{

	/**
	 * Test of accept method, of class PredicateMethod. <br>
	 * <ul>
	 * <li><b>Description : </b>Sets a value for the {@link org.hlib4j.collection.PredicateValue#accept(Object)} and control it</li>
	 * <li><b>Result : </b>Value conforms</li>
	 * <li><b>Comments : </b>None.</li>
	 * </ul>
	 */
	@Test
	public void testAccept()
	{
		PredicateValue<Integer> ref = new PredicateValue<>(5);
		assertTrue( ref.accept( 5 ) );
	}

	/**
	 * Test of accept method, of class PredicateMethod. <br>
	 * <ul>
	 * <li><b>Description : </b>Sets a value for the {@link org.hlib4j.collection.PredicateValue#accept(Object) } and control a different
	 * value</li>
	 * <li><b>Result : </b>Value not conforms</li>
	 * <li><b>Comments : </b>None.</li>
	 * </ul>
	 */
	@Test
	public void testAcceptFalse()
	{
        PredicateValue<Integer> ref = new PredicateValue<>(4);
		Assert.assertFalse( ref.accept( 5 ) );
	}

	/**
	 * Test method for {@link PredicateValue#accept(Object)}.
	 * <ul>
	 * <li><b>Description: </b>Controls if the given value is valid or not</li>
	 * <li><b>Results: </b>Value conforms</li>
	 * <li><b>Comments: </b>None.</li>
	 * </ul>
	 */
	@Test
	public final void testAcceptNull()
	{
        PredicateValue< Object > _ref = new PredicateValue<>( null );
		Assert.assertFalse( _ref.accept( new Object() ) );
	}

	/**
	 * Test method for {@link PredicateValue#accept(Object)}.
	 * <ul>
	 * <li><b>Description: </b>Controls if the given value is not valid</li>
	 * <li><b>Results: </b>Value not conforms</li>
	 * <li><b>Comments: </b>None.</li>
	 * </ul>
	 */
	@Test
	public final void testAcceptFalseNull()
	{
        PredicateValue< Object > _ref = new PredicateValue<>( null );
		Assert.assertTrue( _ref.accept( null ) );
	}
}

