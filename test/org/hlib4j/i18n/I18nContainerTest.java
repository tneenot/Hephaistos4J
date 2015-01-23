package org.hlib4j.i18n;
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

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link I18nContainer} manager class.
 *
 * @author Tioben Neenot
 */
public class I18nContainerTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	/**
	 * Test method for {@link org.hlib4j.i18n.I18nContainer#I18nContainer()}.
	 */
	@Test
	public final void testI18nContainerr()
	{
		Locale _loc = Locale.getDefault();
		I18nContainer _manager = new I18nContainer();

		assertNotNull( _manager );
		assertEquals( _loc, _manager.getLastLocale() );
	}

	/**
	 * Test method for {@link org.hlib4j.i18n.I18nContainer#I18nContainer(java.util.Locale)}.
	 */
	@Test
	public final void testI18nContainerLocale()
	{
		Locale _loc = new Locale( "fr", "FR" );
		I18nContainer _manager = new I18nContainer( new Locale( "fr", "FR" ) );

		assertNotNull( _manager );
		assertEquals( _loc, _manager.getLastLocale() );
	}

	/**
	 * Test method for {@link org.hlib4j.i18n.I18nContainer#I18nContainer(java.util.Locale)}.
	 */
	@Test( expected = NullPointerException.class )
	public final void testI18nContainerLocaleNull()
	{
		new I18nContainer( null );
	}

	/**
	 * Test method for {@link org.hlib4j.i18n.I18nContainer#getLastLocale()}.
	 */
	@Test
	public final void testGetLastLocale()
	{
		assertNotNull( new I18nContainer().getLastLocale() );
	}

	/**
	 * Test method for {@link org.hlib4j.i18n.I18nContainer#add(org.hlib4j.i18n.I18n)}.
	 */
	@Test
	public final void testAdd()
	{
		I18nContainer _manager = new I18nContainer();
		I18nForUT _ref = new I18nForUT();
		assertTrue( _manager.add( _ref ) );
		assertFalse( _manager.add( _ref ) );
		assertTrue( _manager.add( new I18nBisForUT() ) );
	}

	/**
	 * Test method for {@link org.hlib4j.i18n.I18nContainer#add(org.hlib4j.i18n.I18n)}.
	 */
	@Test( expected = NullPointerException.class )
	public final void testAdd_NullBaseName()
	{
		new I18nContainer().add( new I18nInvalid() );
	}

	/**
	 * Test method for {@link org.hlib4j.i18n.I18nContainer#add(org.hlib4j.i18n.I18n)}.
	 */
	@Test( expected = MissingResourceException.class )
	public final void testAdd_EmptyBaseName()
	{
		new I18nContainer().add( new I18nInvalid( 0 ) );
	}

	/**
	 * Test method for {@link org.hlib4j.i18n.I18nContainer#add(org.hlib4j.i18n.I18n)}.
	 */
	@Test
	public final void testAdd_Null()
	{
		I18nContainer _manager = new I18nContainer();
		assertFalse( _manager.add( null ) );
	}

	/**
	 * Test method for {@link org.hlib4j.i18n.I18nContainer#remove(org.hlib4j.i18n.I18n)}.
	 */
	@Test
	public final void testRemove()
	{
		I18nContainer _manager = new I18nContainer();
		I18nForUT _ref = new I18nForUT();
		assertTrue( _manager.add( _ref ) );
		assertTrue( _manager.remove( _ref ) );
		assertFalse( _manager.remove( new I18nBisForUT() ) );
	}

	/**
	 * Test method for {@link org.hlib4j.i18n.I18nContainer#contains(org.hlib4j.i18n.I18n)}.
	 */
	@Test
	public final void testContains()
	{
		I18nContainer _manager = new I18nContainer();
		I18nForUT _ref = new I18nForUT();
		assertTrue( _manager.add( _ref ) );
		assertTrue( _manager.contains( _ref ) );
		assertFalse( _manager.contains( new I18nBisForUT() ) );
	}

	/**
	 * Test method for {@link org.hlib4j.i18n.I18nContainer#size()}.
	 */
	@Test
	public final void testSize()
	{
		I18nContainer _manager = new I18nContainer();
		I18nForUT _ref = new I18nForUT();
		assertTrue( _manager.add( _ref ) );
		assertFalse( _manager.add( _ref ) );
		assertTrue( _manager.add( new I18nBisForUT() ) );

		assertEquals( 2, _manager.size() );
	}

	/**
	 * Test method for {@link org.hlib4j.i18n.I18nContainer#update()}.
	 */
	@Test
	public final void testUpdate()
	{
		I18nContainer _manager = new I18nContainer();
		I18nForUT _ref = new I18nForUT();
		assertTrue( _manager.add( _ref ) );
		assertTrue( _manager.add( new I18nBisForUT() ) );
		_manager.update();
	}

	/**
	 * Test method for {@link org.hlib4j.i18n.I18nContainer#add(org.hlib4j.i18n.I18n) }.
	 */
	@Test( expected = NullPointerException.class )
	public final void testAddInvalid()
	{
		I18nContainer _manager = new I18nContainer();
		I18nForUT _ref = new I18nForUT();
		assertTrue( _manager.add( _ref ) );
		assertTrue( _manager.add( new I18nInvalid() ) );
	}
}

class I18nForUT implements I18n
{
	/*
   * (non-Javadoc)
   * 
   * @see org.hlib4j.i18n.I18n#change(java.util.Locale)
   */

	@Override
	public void change( Locale l )
	{
		ResourceBundle.getBundle( getBaseName(), l );
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.hlib4j.i18n.I18n#getBaseName()
	 */
	@Override
	public String getBaseName()
	{
		return "org.hlib4j.i18n.bundleTest";
	}
}

class I18nBisForUT implements I18n
{
  /*
   * (non-Javadoc)
   * 
   * @see org.hlib4j.i18n.I18n#change(java.util.Locale)
   */

	@Override
	public void change( Locale l )
	{
		ResourceBundle.getBundle( getBaseName(), l );
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.hlib4j.i18n.I18n#getBaseName()
	 */
	@Override
	public String getBaseName()
	{
		return "org.hlib4j.i18n.bundleTest";
	}
}

/**
 * Class test that's implements the I18n concepts.
 *
 * @author Tioben Neenot
 */
class I18nInvalid implements I18n
{

	private final String baseName;

	public I18nInvalid()
	{
		this.baseName = null;
	}

	public I18nInvalid( int v )
	{
		this.baseName = "";
	}

	@Override
	public void change( Locale l )
	{
		ResourceBundle.getBundle( getBaseName(), l );
	}

	@Override
	public String getBaseName()
	{
		return baseName;
	}
}
