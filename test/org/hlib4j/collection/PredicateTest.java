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
 * Unit tests for {@link Predicate} class.
 *
 * @author Tioben Neenot
 */
public class PredicateTest {

    @Test
    public void test_Accept_ValidValueAccepted() {
        Predicate<Integer> ref = new Predicate<>(5);
        assertTrue(ref.accept(5));
    }

    @Test
    public void test_Accept_InvalidValueNotAccepted() {
        Predicate<Integer> ref = new Predicate<>(4);
        Assert.assertFalse(ref.accept(5));
    }

    @Test
    public final void test_Accept_InvalidObjectValueNotAccepted() {
        Predicate<Object> _ref = new Predicate<>(null);
        Assert.assertFalse(_ref.accept(new Object()));
    }

    @Test
    public final void test_Accept_ValidNullValueAccepted() {
        Predicate<Object> _ref = new Predicate<>(null);
        Assert.assertTrue(_ref.accept(null));
    }
}

