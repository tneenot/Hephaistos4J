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

import org.junit.*;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link I18nLayout} class.
 *
 * @author Tioben Neenot
 */
public class I18nLayoutTest
{

	public I18nLayoutTest()
	{
	}

	@BeforeClass
	public static void setUpClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownClass() throws Exception
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
	 * Test of change method, of class I18nLayout. Uses an empty component.
	 */
	@Test
	public void testChange()
	{
		System.out.println( "change" );
		Locale l = new Locale( "fr", "FR_EURO" );
		I18nLayout instance = new I18nLayout( "org.hlib4j.i18n.messages", new JPanel() );
		instance.change( l );
	}

	/**
	 * Test of getBaseName method, of class I18nLayout.
	 */
	@Test
	public void testGetBaseName()
	{
		System.out.println( "getBaseName" );
		I18nLayout instance = new I18nLayout( "org.hlib4j.i18n.messages", new JPanel() );
		String expResult = "org.hlib4j.i18n.messages";
		String result = instance.getBaseName();
		assertEquals( expResult, result );
	}

	/**
	 * Test of addLayoutComponent method, of class I18nLayout.
	 */
	@Test
	public void testAddLayoutComponent()
	{
		System.out.println( "addLayoutComponent" );
		String name = "btnTest";
		Component comp = new JButton( "testing" );
		I18nLayout instance = new I18nLayout( "org.hlib4j.i18n.messages", new JPanel() );
		instance.addLayoutComponent( name, comp );
	}

	/**
	 * Test of removeLayoutComponent method, of class I18nLayout.
	 */
	@Test
	public void testRemoveLayoutComponent()
	{
		System.out.println( "removeLayoutComponent" );
		Component comp = new JButton( "testing" );
		I18nLayout instance = new I18nLayout( "org.hlib4j.i18n.test", new JPanel() );
		instance.addLayoutComponent( "btnTest", comp );
		instance.removeLayoutComponent( comp );
	}

	/**
	 * Test of preferredLayoutSize method, of class I18nLayout.
	 */
	@Test
	public void testPreferredLayoutSize()
	{
		System.out.println( "preferredLayoutSize" );
		Container parent = new JPanel();
		I18nLayout instance = new I18nLayout( "org.hlib4j.i18n.messages", new JPanel() );
		parent.setLayout( instance );
		Dimension expResult = new Dimension( 10, 10 );
		Dimension result = instance.preferredLayoutSize( parent );
		assertEquals( expResult, result );
	}

	/**
	 * Test of minimumLayoutSize method, of class I18nLayout.
	 */
	@Test
	public void testMinimumLayoutSize()
	{
		System.out.println( "minimumLayoutSize" );
		Container parent = new JPanel();
		I18nLayout instance = new I18nLayout( "org.hlib4j.i18n.messages", new JPanel() );
		parent.setLayout( instance );
		Dimension expResult = new Dimension( 10, 10 );
		Dimension result = instance.minimumLayoutSize( parent );
		assertEquals( expResult, result );
	}

	/**
	 * Test of layoutContainer method, of class I18nLayout.
	 */
	@Test
	public void testLayoutContainer()
	{
		System.out.println( "layoutContainer" );
		Container parent = new Panel();
		I18nLayout instance = new I18nLayout( "org.hlib4j.i18n.messages", new JPanel() );
		instance.layoutContainer( parent );
	}

	/**
	 * Test of layoutContainer method, of class I18nLayout. Takes a null target container
	 */
	@Test( expected = NullPointerException.class )
	public void testLayoutContainerNull()
	{
		System.out.println( "layoutContainer" );
		Container parent = new Panel();
		I18nLayout instance = new I18nLayout( "org.hlib4j.i18n.messages", null );
		instance.layoutContainer( parent );
	}
}
