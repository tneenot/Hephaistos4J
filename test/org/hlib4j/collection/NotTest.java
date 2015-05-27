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
	public void setUp() {
		this.ref = new Not<>(5);
	}

	/**
	 * Test cleaning
	 */
	@After
	public void tearDown()
	{
		this.ref = null;
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
    public void test_Accept_ValidValueAccepted() {
		Assert.assertTrue(this.ref.accept(4));
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
    public void test_Accept_InvalidValueNotAccepted() {
		Assert.assertFalse(this.ref.accept(5));
	}

	@Test
	public void test_Accept_NullAccepted() {
		Assert.assertTrue(this.ref.accept(null));
	}
}

