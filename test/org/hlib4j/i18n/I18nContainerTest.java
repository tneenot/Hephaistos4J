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

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link I18nContainer} manager class.
 *
 * @author Tioben Neenot
 */
public class I18nContainerTest {

    /**
     * Test method for {@link org.hlib4j.i18n.I18nContainer#I18nContainer()}.
     */
    @Test
    public final void test_Constructor_DefaultLocale_AwaitingLocalByDefault() {
        Locale _loc = Locale.getDefault();
        I18nContainer _manager = new I18nContainer();
        assertEquals(_loc, _manager.getLastLocale());
    }

    /**
     * Test method for {@link org.hlib4j.i18n.I18nContainer#I18nContainer(java.util.Locale)}.
     */
    @Test
    public final void test_Constructor_SetNewLocale_LocalUpdated() {
        Locale _loc = new Locale("fr", "FR");
        I18nContainer _manager = new I18nContainer(new Locale("fr", "FR"));
        assertEquals(_loc, _manager.getLastLocale());
    }

    /**
     * Test method for {@link org.hlib4j.i18n.I18nContainer#I18nContainer(java.util.Locale)}.
     */
    @Test(expected = NullPointerException.class)
    public final void test_Constructor_NullLocale_NullPointerException() {
        new I18nContainer(null);
    }

    /**
     * Test method for {@link org.hlib4j.i18n.I18nContainer#getLastLocale()}.
     */
    @Test
    public final void test_GetLastLocale_ForValidLocal_NotNull() {
        assertNotNull(new I18nContainer().getLastLocale());
    }

    /**
     * Test method for {@link org.hlib4j.i18n.I18nContainer#add(org.hlib4j.i18n.I18n)}.
     */
    @Test
    public final void test_Add_ValidI18nType_Accepted() {
        I18nContainer _manager = new I18nContainer();
        I18nFake _ref = new I18nFake();
        assertTrue(_manager.add(_ref));
    }

    /**
     * Test method for {@link org.hlib4j.i18n.I18nContainer#add(org.hlib4j.i18n.I18n)}.
     */
    @Test
    public final void test_Add_ValidI18nType_FalseSinceAddedTwice() {
        I18nContainer _manager = new I18nContainer();
        I18nFake _ref = new I18nFake();
        _manager.add(_ref);
        assertFalse(_manager.add(_ref));
    }

    /**
     * Test method for {@link org.hlib4j.i18n.I18nContainer#add(org.hlib4j.i18n.I18n)}.
     */
    @Test(expected = NullPointerException.class)
    public final void test_Add_NullBaseName_NullPointerException() {
        new I18nContainer().add(new I18nInvalidFake());
    }

    /**
     * Test method for {@link org.hlib4j.i18n.I18nContainer#add(org.hlib4j.i18n.I18n)}.
     */
    @Test(expected = MissingResourceException.class)
    public final void test_Add_EmptyBaseName_MissingResourceException() {
        new I18nContainer().add(new I18nInvalidFake(0));
    }

    /**
     * Test method for {@link org.hlib4j.i18n.I18nContainer#add(org.hlib4j.i18n.I18n)}.
     */
    @Test
    public final void test_Add_NullValue_NotAccepted() {
        I18nContainer _manager = new I18nContainer();
        assertFalse(_manager.add(null));
    }

    /**
     * Test method for {@link org.hlib4j.i18n.I18nContainer#remove(org.hlib4j.i18n.I18n)}.
     */
    @Test
    public final void test_Remove_ValidValue_ValueRemoved() {
        I18nContainer _manager = new I18nContainer();
        I18nFake _ref = new I18nFake();
        _manager.add(_ref);
        assertTrue(_manager.remove(_ref));
        assertFalse(_manager.remove(new I18nBisFake()));
    }

    /**
     * Test method for {@link org.hlib4j.i18n.I18nContainer#remove(org.hlib4j.i18n.I18n)}.
     */
    @Test
    public final void test_Remove_NotExistingValue_NoRemove() {
        I18nContainer _manager = new I18nContainer();
        I18nFake _ref = new I18nFake();
        _manager.add(_ref);
        assertFalse(_manager.remove(new I18nBisFake()));
    }

    /**
     * Test method for {@link org.hlib4j.i18n.I18nContainer#contains(org.hlib4j.i18n.I18n)}.
     */
    @Test
    public final void test_Contains_ValidValue_ValueFound() {
        I18nContainer _manager = new I18nContainer();
        I18nFake _ref = new I18nFake();
        _manager.add(_ref);
        assertTrue(_manager.contains(_ref));
    }

    @Test
    public final void test_Contains_ForNotExistingValue_ValueNotExist() {
        I18nContainer _manager = new I18nContainer();
        I18nFake _ref = new I18nFake();
        _manager.add(_ref);
        assertFalse(_manager.contains(new I18nBisFake()));
    }

    /**
     * Test method for {@link org.hlib4j.i18n.I18nContainer#size()}.
     */
    @Test
    public final void test_Size_WithSameReferencesAddedTwice_RedudantReferencesNotTakingAccount() {
        I18nContainer _manager = new I18nContainer();
        I18nFake _ref = new I18nFake();
        _manager.add(_ref);
        _manager.add(_ref);
        _manager.add(new I18nBisFake());

        assertEquals(2, _manager.size());
    }

    /**
     * Test method for {@link org.hlib4j.i18n.I18nContainer#update()}.
     */
    @Test
    public final void test_Update_WithValidI18nReference_NoError() {
        I18nContainer _manager = new I18nContainer();
        I18nFake _ref = new I18nFake();
        _manager.add(_ref);
        _manager.update();
    }

    /**
     * Test method for {@link org.hlib4j.i18n.I18nContainer#add(org.hlib4j.i18n.I18n) }.
     */
    @Test(expected = NullPointerException.class)
    public final void test_Add_InvalidI18nBaseName_NullPointerException() {
        I18nContainer _manager = new I18nContainer();
        I18nFake _ref = new I18nFake();
        _manager.add(new I18nInvalidFake());
    }
}

class I18nFake implements I18n {
    /*
   * (non-Javadoc)
   * 
   * @see org.hlib4j.i18n.I18n#change(java.util.Locale)
   */

    @Override
    public void change(Locale l) {
        ResourceBundle.getBundle(getBaseName(), l);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.hlib4j.i18n.I18n#getBaseName()
     */
    @Override
    public String getBaseName() {
        return "org.hlib4j.i18n.bundleTest";
    }
}

class I18nBisFake implements I18n {
  /*
   * (non-Javadoc)
   * 
   * @see org.hlib4j.i18n.I18n#change(java.util.Locale)
   */

    @Override
    public void change(Locale l) {
        ResourceBundle.getBundle(getBaseName(), l);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.hlib4j.i18n.I18n#getBaseName()
     */
    @Override
    public String getBaseName() {
        return "org.hlib4j.i18n.bundleTest";
    }
}

/**
 * Class test that's implements the I18n concepts.
 *
 * @author Tioben Neenot
 */
class I18nInvalidFake implements I18n {

    private final String baseName;

    public I18nInvalidFake() {
        this.baseName = null;
    }

    public I18nInvalidFake(int v) {
        this.baseName = "";
    }

    @Override
    public void change(Locale l) {
        ResourceBundle.getBundle(getBaseName(), l);
    }

    @Override
    public String getBaseName() {
        return baseName;
    }
}
