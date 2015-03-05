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

import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link I18nLayout} class.
 *
 * @author Tioben Neenot
 */
public class I18nLayoutTest {

    /**
     * Test of change method, of class I18nLayout. Uses an empty component.
     */
    @Test
    public void test_Change_WithoutError() {
        Locale l = new Locale("fr", "FR_EURO");
        I18nLayout instance = new I18nLayout("org.hlib4j.i18n.messages", new JPanel());
        instance.change(l);
    }

    /**
     * Test of getBaseName method, of class I18nLayout.
     */
    @Test
    public void test_GetBaseName_ValidValue() {
        I18nLayout instance = new I18nLayout("org.hlib4j.i18n.messages", new JPanel());
        String expResult = "org.hlib4j.i18n.messages";
        String result = instance.getBaseName();
        assertEquals(expResult, result);
    }

    /**
     * Test of addLayoutComponent method, of class I18nLayout.
     */
    @Test
    public void test_AddLayoutComponent_WithoutError() {
        String name = "btnTest";
        Component comp = new JButton("testing");
        I18nLayout instance = new I18nLayout("org.hlib4j.i18n.messages", new JPanel());
        instance.addLayoutComponent(name, comp);
    }

    /**
     * Test of removeLayoutComponent method, of class I18nLayout.
     */
    @Test
    public void test_RemoveLayoutComponent_WithoutError() {
        Component comp = new JButton("testing");
        I18nLayout instance = new I18nLayout("org.hlib4j.i18n.test", new JPanel());
        instance.addLayoutComponent("btnTest", comp);
        instance.removeLayoutComponent(comp);
    }

    /**
     * Test of preferredLayoutSize method, of class I18nLayout.
     */
    @Test
    public void test_PreferredLayoutSize() {
        Container parent = new JPanel();
        I18nLayout instance = new I18nLayout("org.hlib4j.i18n.messages", new JPanel());
        parent.setLayout(instance);
        Dimension expResult = new Dimension(10, 10);
        Dimension result = instance.preferredLayoutSize(parent);
        assertEquals(expResult, result);
    }

    /**
     * Test of minimumLayoutSize method, of class I18nLayout.
     */
    @Test
    public void test_MinimumLayoutSize() {
        Container parent = new JPanel();
        I18nLayout instance = new I18nLayout("org.hlib4j.i18n.messages", new JPanel());
        parent.setLayout(instance);
        Dimension expResult = new Dimension(10, 10);
        Dimension result = instance.minimumLayoutSize(parent);
        assertEquals(expResult, result);
    }

    /**
     * Test of layoutContainer method, of class I18nLayout.
     */
    @Test
    public void test_LayoutContainer_WithoutError() {
        Container parent = new Panel();
        I18nLayout instance = new I18nLayout("org.hlib4j.i18n.messages", new JPanel());
        instance.layoutContainer(parent);
    }

    /**
     * Test of layoutContainer method, of class I18nLayout. Takes a null target container
     */
    @Test(expected = NullPointerException.class)
    public void test_LayoutContainer_NullPointerException() {
        Container parent = new Panel();
        I18nLayout instance = new I18nLayout("org.hlib4j.i18n.messages", null);
        instance.layoutContainer(parent);
    }
}
